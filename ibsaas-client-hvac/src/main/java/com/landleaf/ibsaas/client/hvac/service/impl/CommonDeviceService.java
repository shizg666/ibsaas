package com.landleaf.ibsaas.client.hvac.service.impl;


import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.client.hvac.config.HvacNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.HvacPointHolder;
import com.landleaf.ibsaas.client.hvac.config.RemoteDeviceInfoHolder;
import com.landleaf.ibsaas.client.hvac.config.LocalDeviceConfig;
import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.util.BacnetUtil;
import com.landleaf.ibsaas.client.hvac.util.HvacUtil;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;

import com.landleaf.ibsaas.common.domain.hvac.assist.HvacPointDetail;
import com.landleaf.ibsaas.common.domain.hvac.vo.*;

import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.landleaf.ibsaas.common.constant.HvacConstant.*;


/**
 * @author Lokiy
 * @date 2019/5/28 15:41
 * @description:
 */
@Service
//@AllArgsConstructor
@Slf4j
public class CommonDeviceService implements ICommonDeviceService {

    @Autowired
    private HvacDeviceDao hvacDeviceDao;

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Autowired
    private RemoteDeviceInfoHolder remoteDeviceInfoHolder;
    @Autowired
    private HvacNodeHolder hvacNodeHolder;
    @Autowired
    private HvacPointHolder hvacPointHolder;

    @Override
    public void reload() {
        remoteDeviceInfoHolder.reload();
        hvacNodeHolder.reload();
        hvacPointHolder.reload();
    }


    @Override
    public List<? extends BaseDevice> getCurrentData(Integer deviceType) {
        List<HvacPointDetail> hvacPointDetails = HvacPointHolder.DEVICE_POINT_LIST_MAP.get(deviceType);
        Map<String, List<HvacPointDetail>> hvacPointMap = HvacPointHolder.DEVICE_POINT_MAP.get(deviceType);
        Map<String, PropertyValues> propertyValues = getPropertyValues(hvacPointDetails);

        List<BaseDevice> result = HvacNodeHolder.DEVICE_NODE_MAP.get(deviceType);

        result.forEach(bd -> {
            List<HvacPointDetail> hvacPointDetailList = hvacPointMap.get(bd.getId());
            HvacUtil.assignmentByClassEx(propertyValues, hvacPointDetailList, bd);
        });
        return result;
    }

    private Map<String, PropertyValues> getPropertyValues(List<HvacPointDetail> hvacPointDetails){
        Map<String, PropertyValues> result = new HashMap<>(8);
        HashMap<String, List<ObjectIdentifier>> objectIdentifierMap =
                hvacPointDetails.stream().collect(
                        Collectors.groupingBy(
                                HvacPointDetail::getDeviceId,
                                HashMap::new,
                                Collectors.mapping(hp ->
                                        new ObjectIdentifier(
                                                BacnetObjectEnum.getObjectType(hp.getBacnetObjectType()),
                                                hp.getInstanceNumber()),
                                        Collectors.toList())));
        objectIdentifierMap.forEach((k,v) -> {
            try {
                PropertyValues values = BacnetUtil.readPresentValues(LocalDeviceConfig.getLocalDevice(), RemoteDeviceInfoHolder.REMOTE_DEVICE_ID_MAP.get(k), v);
                result.put(k, values);
            } catch (BACnetException e) {
                e.printStackTrace();
                log.error("------------------------------>请求设备点位事发生错误:{}",e.getMessage(), e);
            }

        });

        return result;
    }


    @Override
    public void currentDataToRedis() {
        //转成collection开启并行处理流   平均处理时间在500ms
        BacnetDeviceTypeEnum.MAP.entrySet().parallelStream().forEach(entry -> {
            List<? extends BaseDevice> currentData = getCurrentData(entry.getKey());
            if (CollectionUtils.isNotEmpty(currentData)) {
                redisHandle.addMap(placeId, String.valueOf(entry.getKey()), currentData);
            }
        });
    }


    @Override
    public <T extends BaseDevice> void writeDevice(T t){
        //主键id
        String id = t.getId();
        Field[] fields = ReflectUtil.getFields(t.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String value = (String) field.get(t);
                if(value != null && !"".equals(value)){
                    //不为空
                    String name = field.getName();
                    if("id".equals(field.getName())){
                        continue;
                    }
                    writeField(id, name, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("反射对象取值时,发生错误");
            }
        }
    }

    /**
     *  根据反射获取的节点属性 获取点位信息 并写入
     * @param id
     * @param fieldName
     * @param value
     * @return
     */
    public boolean writeField(String id, String fieldName, String value){
        if(exWriteLogic( id, fieldName, value)){
            //符合额外操作的  直接返回
            return true;
        }
        HvacNodeFieldVO hvacNodeFieldVO = hvacNodeDao.getHvacNodeFieldVO(id, fieldName);
        BacnetUtil.writePresentValue(LocalDeviceConfig.getLocalDevice(),
                RemoteDeviceInfoHolder.REMOTE_DEVICE_ID_MAP.get(hvacNodeFieldVO.getDeviceId()),
                new ObjectIdentifier(BacnetObjectEnum.getObjectType(hvacNodeFieldVO.getBacnetObjectType()),hvacNodeFieldVO.getInstanceNumber()),
                value);
        return true;
    }


    public boolean exWriteLogic(String id, String fieldName, String value){
        //这里应该是根据id查找到具体是某个设备，再根据设备的fieldName进行特殊处理

        //新风机特殊逻辑
        if("runningMode".equals(fieldName)){
            //新风机运行模式  特殊处理
            List<HvacNodeFieldVO> hnfs = hvacNodeDao.getHvacNodeFieldVOByFieldName(fieldName);
            hnfs.forEach( hnf -> BacnetUtil.writePresentValue(LocalDeviceConfig.getLocalDevice(),
                    RemoteDeviceInfoHolder.REMOTE_DEVICE_ID_MAP.get(hnf.getDeviceId()),
                    new ObjectIdentifier(BacnetObjectEnum.getObjectType(hnf.getBacnetObjectType()),hnf.getInstanceNumber()),
                    value));
            return true;
        }

        if("humOnOff".equals(fieldName)){
            //新风机 加湿阀  特殊处理
        }


        //水力模块/毛细管特殊逻辑
//        if("hmMachRunningMode".equals(fieldName)||"hmPdewRunningMode".equals(fieldName)){
//            List<HvacNodeFieldVO> hnfs = hvacNodeDao.getHvacNodeFieldVOList(id, new ArrayList<String>() {{
//                add("hmMachRunningMode");
//                add("hmPdewRunningMode");
//            }});
//            hnfs.forEach( hnf -> BacnetUtil.writePresentValue(LocalDeviceConfig.getLocalDevice(),
//                    BacnetInfoHolder.REMOTE_DEVICE_MAP.get(hnf.getDeviceInstanceNumber()),
//                    new ObjectIdentifier(BacnetObjectEnum.getObjectType(hnf.getBacnetObjectType()),hnf.getInstanceNumber()),
//                    value));
//            return true;
//        }

        return false;
    }






























    /*
        以下为弃用代码
     */

    //    @Override
//    @Deprecated
//    public List<? extends BaseDevice> getCurrentData2(Integer deviceInstanceNumber) {
//        List<BaseDevice> result = new ArrayList<>();
//        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), RemoteDeviceInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), RemoteDeviceInfoHolder.OID_MAP.get(deviceInstanceNumber));
//        HvacDevice hvacDevice = hvacDeviceDao.getByDeviceInstanceNumber(deviceInstanceNumber);
//        List<HvacNodeVO> hvacNodeVOList = hvacNodeDao.getHvacNodeByDeviceId(hvacDevice.getId());
//
//        hvacNodeVOList.forEach( bdn -> {
//            BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
//            baseDevice.setId(bdn.getId());
//            List<HvacFieldVO> hvacFieldVOList = bdn.getHvacFieldVOList();
//            HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
//            result.add(baseDevice);
//        });
//        return result;
//    }

//    @Deprecated
//    public <T extends BaseDevice> T getCurrentInfo(HvacNodeVO hvacNodeVO){
//        HvacDevice hvacDevice = hvacDeviceDao.selectByPrimaryKey(hvacNodeVO.getDeviceId());
//        Integer deviceInstanceNumber = hvacDevice.getDeviceInstanceNumber();
//        BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
//        baseDevice.setId(hvacNodeVO.getId());
//        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), RemoteDeviceInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), RemoteDeviceInfoHolder.OID_MAP.get(deviceInstanceNumber));
//        List<HvacFieldVO> hvacFieldVOList = hvacNodeVO.getHvacFieldVOList();
//        HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
//        return (T) baseDevice;
//    }

    //    @Override
//    @Deprecated
//    public void currentDataToRedis2(){
//        Map<Integer, RemoteDevice> remoteDeviceMap = RemoteDeviceInfoHolder.REMOTE_DEVICE_MAP;
//        //转成collection开启并行处理流   平均处理时间在500ms
//        remoteDeviceMap.entrySet().parallelStream().forEach( entry -> {
//            List<? extends BaseDevice> currentData = getCurrentData(entry.getKey());
//            if(CollectionUtils.isNotEmpty(currentData)) {
//                redisHandle.addMap(placeId, String.valueOf(entry.getKey()), currentData);
//            }
//        });
//
//
//        //map并行流处理  平均处理时间在1.3s
////        remoteDeviceMap.forEachEntry(remoteDeviceMap.mappingCount(),entry -> {
////
////            List<? extends BaseDevice> currentData = getCurrentData(entry.getKey());
////            if(CollectionUtils.isNotEmpty(currentData)) {
////                redisHandle.addMap(placeId, String.valueOf(entry.getKey()), currentData);
////            }
////
////        });
//
//        //普通循环 平均处理时间在1.6s
////        remoteDeviceMap.forEach( (k, v) -> {
////            List<? extends BaseDevice> currentData = getCurrentData(k);
////            if(CollectionUtils.isNotEmpty(currentData)) {
////                redisHandle.addMap(placeId, Integer.toString(k), currentData);
////            }
////
////        });
//    }

//    /**
//     * 存入的设备
//     * @param deviceInstanceNumber
//     * @return
//     */
//    @Deprecated
//    private BaseDevice getByDeviceId(Integer deviceInstanceNumber){
//        switch (deviceInstanceNumber) {
//            case NEW_FAN_PORT:
//                return new NewFanVO();
//            case FAN_COIL_PORT_1:
//            case FAN_COIL_PORT_2:
//                return new FanCoilVO();
//            case WEATHER_STATION_PORT:
//                return new WeatherStationVO();
//            case HYDRAULIC_MODULE_PORT :
//                return new HydraulicModuleVO();
//            case WATER_METER_PORT:
//                return new WaterMeterVO();
//            case ELECTRIC_METER_PORT:
//                return new ElectricMeterVO();
//            default:
//                return new BaseDevice();
//        }
//    }

}
