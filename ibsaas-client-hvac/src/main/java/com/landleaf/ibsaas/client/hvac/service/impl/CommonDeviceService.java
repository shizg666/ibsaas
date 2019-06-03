package com.landleaf.ibsaas.client.hvac.service.impl;


import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.client.hvac.config.BacnetInfoHolder;
import com.landleaf.ibsaas.client.hvac.config.LocalDeviceConfig;
import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.util.BacnetUtil;
import com.landleaf.ibsaas.client.hvac.util.HvacUtil;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;

import com.landleaf.ibsaas.common.domain.hvac.vo.HvacFieldVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeFieldVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;

import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


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

    @Override
    public List<? extends BaseDevice> getCurrentData(Integer deviceInstanceNumber) {
        List<BaseDevice> result = new ArrayList<>();
        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), BacnetInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), BacnetInfoHolder.OID_MAP.get(deviceInstanceNumber));
        HvacDevice hvacDevice = hvacDeviceDao.getByDeviceInstanceNumber(deviceInstanceNumber);
        List<HvacNodeVO> hvacNodeVOList = hvacNodeDao.getHvacNodeByDeviceId(hvacDevice.getId());

        hvacNodeVOList.forEach( bdn -> {
            BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
            baseDevice.setId(bdn.getId());
            List<HvacFieldVO> hvacFieldVOList = bdn.getHvacFieldVOList();
            HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
            result.add(baseDevice);
        });
        return result;
    }


    @Override
    public <T extends BaseDevice> T getCurrentInfo(HvacNodeVO hvacNodeVO){
        HvacDevice hvacDevice = hvacDeviceDao.selectByPrimaryKey(hvacNodeVO.getDeviceId());
        Integer deviceInstanceNumber = hvacDevice.getDeviceInstanceNumber();
        BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
        baseDevice.setId(hvacNodeVO.getId());
        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), BacnetInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), BacnetInfoHolder.OID_MAP.get(deviceInstanceNumber));
        List<HvacFieldVO> hvacFieldVOList = hvacNodeVO.getHvacFieldVOList();
        HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
        return (T) baseDevice;
    }



    @Override
    public void currentDataToRedis(){
        ConcurrentHashMap<Integer, RemoteDevice> remoteDeviceMap = BacnetInfoHolder.REMOTE_DEVICE_MAP;
        remoteDeviceMap.forEachEntry(remoteDeviceMap.mappingCount(),entry -> {
            List<? extends BaseDevice> currentData = getCurrentData(entry.getKey());
            if(CollectionUtils.isNotEmpty(currentData)) {
                redisHandle.addMap(placeId, String.valueOf(entry.getKey()), currentData);
            }

        });
//        remoteDeviceMap.forEach( (k, v) -> {
//            List<? extends BaseDevice> currentData = getCurrentData(k);
//            if(CollectionUtils.isNotEmpty(currentData)) {
//                redisHandle.addMap(placeId, Integer.toString(k), currentData);
//            }
//
//        });



    }


    private BaseDevice getByDeviceId(Integer deviceInstanceNumber){
        switch (deviceInstanceNumber) {
            case 3002:
                return new NewFanVO();
            default:
                return new BaseDevice();
        }
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
        HvacNodeFieldVO hvacNodeFieldVO = hvacNodeDao.getHvacNodeFieldVO(id, fieldName);
        BacnetUtil.writePresentValue(LocalDeviceConfig.getLocalDevice(),
                BacnetInfoHolder.REMOTE_DEVICE_MAP.get(hvacNodeFieldVO.getDeviceInstanceNumber()),
                new ObjectIdentifier(BacnetObjectEnum.getObjectType(hvacNodeFieldVO.getBacnetObjectType()),hvacNodeFieldVO.getInstanceNumber()),
                value);
        return true;
    }
}
