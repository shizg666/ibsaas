package com.landleaf.ibsaas.client.hvac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.landleaf.ibsaas.client.hvac.config.HvacNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.HvacPointHolder;
import com.landleaf.ibsaas.client.hvac.config.LocalDeviceConfig;
import com.landleaf.ibsaas.client.hvac.config.RemoteDeviceInfoHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbMasterHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbRegisterHolder;
import com.landleaf.ibsaas.client.hvac.util.BacnetUtil;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.client.hvac.util.HvacUtil;
import com.landleaf.ibsaas.client.hvac.util.ModbusUtil;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.hvac.EqpDataDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.EqpData;
import com.landleaf.ibsaas.common.domain.hvac.assist.HvacPointDetail;
import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ProtocolTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/7/9 17:02
 * @description:
 */
@Service
@Slf4j
public class CommonAsyncService {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Autowired
    private DaoAdapter<EqpData> daoAdapter;

    @Autowired
    private EqpDataDao eqpDataDao;

    private void copyList(List<? extends BaseDevice> currentData, List<? extends BaseDevice> oriData){
        Map<String, ? extends BaseDevice> tempMap = currentData.stream().collect(Collectors.toMap(BaseDevice::getId, b -> b));
        oriData.forEach(o -> {
            BaseDevice baseDevice = tempMap.get(o.getId());
            BeanUtil.copyProperties(baseDevice, o, CopyOptions.create().ignoreNullValue().ignoreError());
        });

    }


    @Async("taskExecutor")
    public void currentOneMbDataToRedis(Integer deviceType){
        List<? extends BaseDevice> currentData = getMbCurrentData(deviceType);
        List<? extends BaseDevice> oriData = redisHandle.getMapField(placeId, String.valueOf(deviceType));
        oriData = oriData == null ? MbNodeHolder.MODBUS_NODE_MAP.get(deviceType) : oriData;
        if (CollectionUtils.isNotEmpty(currentData)) {
            copyList(currentData, oriData);
            redisHandle.addMap(placeId, String.valueOf(deviceType), oriData);
        }
    }

    @Async("taskExecutor")
    public void currentOneBacnetDataToRedis(Integer deviceType){
        List<? extends BaseDevice> currentData = getCurrentData(deviceType);
        List<? extends BaseDevice> oriData = redisHandle.getMapField(placeId, String.valueOf(deviceType));
        oriData = oriData == null ? HvacNodeHolder.DEVICE_NODE_MAP.get(deviceType) : oriData;
        if (CollectionUtils.isNotEmpty(currentData)) {
            copyList(currentData, oriData);
            redisHandle.addMap(placeId, String.valueOf(deviceType), oriData);
        }
    }


    @Async("taskExecutor")
    public void currentOneMbDataToDatabase(Integer deviceType, Date date) {
        List<MbRegisterDetail> mbRegisterDetails = MbRegisterHolder.MASTER_POINT_LIST_MAP.get(deviceType);
        Map<String, BatchResults<String>> results  = getBatchResults(mbRegisterDetails);
        mbRegisterDetails.forEach( d -> {
            String value = String.valueOf(results.get(d.getMasterId()).getValue(d.getRegisterId()));
            EqpData record = getEqpData(ProtocolTypeEnum.MODBUS, d, value, date);
            insertCurrentData(record);
        });
    }

    @Async("taskExecutor")
    public void currentOneBacnetDataToDatabase(Integer deviceType, Date date) {
        List<HvacPointDetail> hvacPointDetails = HvacPointHolder.DEVICE_POINT_LIST_MAP.get(deviceType);
        Map<String, PropertyValues> propertyValues = getPropertyValues(hvacPointDetails);
        hvacPointDetails.forEach(d -> {
            String value = BacnetUtil.getState(propertyValues.get(d.getDeviceId())
                    .getString(new ObjectIdentifier(
                            BacnetObjectEnum.OBJECT_TYPE_MAP.get(d.getBacnetObjectType()),
                            d.getInstanceNumber()),
                            PropertyIdentifier.presentValue));
            EqpData record = getEqpData(ProtocolTypeEnum.BACNET, d, value, date);
            insertCurrentData(record);
        });
    }


    /**
     * 填充入库
     * @param record
     */
    private void insertCurrentData(EqpData record){
        daoAdapter.consummateAddOperation(record);
        try {
            eqpDataDao.insertSelective(record);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            log.warn("------------------------------>CommonAsyncService.insertCurrentData数据记录已存在");
        }
    }


    /**
     * 初始化入库程序
     * @param protocolTypeEnum
     * @param detail
     * @param value
     * @param date
     * @return
     */
    private EqpData getEqpData(ProtocolTypeEnum protocolTypeEnum, Object detail, String value, Date date){
        EqpData eqpData = new EqpData();
        eqpData.setEqpDataTime(date);
        eqpData.setEqpDataDate(date);
        eqpData.setEqpDataMonth(CalendarUtil.getYearAndMonth(date));
        eqpData.setEqpDataYear(CalendarUtil.year(date));
        eqpData.setEqpDataSource(IbsaasConstant.ENERGY_DATA_SOURCE_1);
        eqpData.setEqpDataValue(value);

        switch (protocolTypeEnum){
            case BACNET:
                eqpData.setProtocolType(ProtocolTypeEnum.BACNET.getProtocolType());

                HvacPointDetail hvacPointDetail = (HvacPointDetail) detail;
                eqpData.setDeviceType(hvacPointDetail.getDeviceType());
                eqpData.setPointId(hvacPointDetail.getPointId());
                eqpData.setHostId(hvacPointDetail.getDeviceId());
                eqpData.setNodeId(hvacPointDetail.getNodeId());
                eqpData.setFieldId(hvacPointDetail.getFieldId());
                eqpData.setNodeName(hvacPointDetail.getNodeName());
                eqpData.setFloor(hvacPointDetail.getFloor());
                eqpData.setFieldName(hvacPointDetail.getFieldName());
                eqpData.setFieldDescription(hvacPointDetail.getFieldDescription());
                break;
            case MODBUS:
                eqpData.setProtocolType(ProtocolTypeEnum.MODBUS.getProtocolType());

                MbRegisterDetail mbRegisterDetail = (MbRegisterDetail) detail;
                eqpData.setDeviceType(mbRegisterDetail.getMbType());
                eqpData.setPointId(mbRegisterDetail.getRegisterId());
                eqpData.setHostId(mbRegisterDetail.getMasterId());
                eqpData.setNodeId(mbRegisterDetail.getNodeId());
                eqpData.setFieldId(mbRegisterDetail.getFieldId());
                eqpData.setNodeName(mbRegisterDetail.getNodeName());
                eqpData.setFloor(mbRegisterDetail.getFloor());
                eqpData.setFieldName(mbRegisterDetail.getFieldName());
                eqpData.setFieldDescription(mbRegisterDetail.getFieldDescription());
                break;
            case COM:
                eqpData.setProtocolType(ProtocolTypeEnum.COM.getProtocolType());
                break;
                default:
                    break;
        }
        return eqpData;
    }




    public List<? extends BaseDevice> getCurrentData(Integer deviceType) {
        List<HvacPointDetail> hvacPointDetails = HvacPointHolder.DEVICE_POINT_LIST_MAP.get(deviceType);
        Map<String, List<HvacPointDetail>> hvacPointMap = HvacPointHolder.DEVICE_POINT_MAP.get(deviceType);
        Map<String, PropertyValues> propertyValues = getPropertyValues(hvacPointDetails);

        List<BaseDevice> result = HvacNodeHolder.DEVICE_NODE_MAP.get(deviceType);

        result.forEach(bd -> {
            List<HvacPointDetail> hvacPointDetailList = hvacPointMap.get(bd.getId());
            HvacUtil.assignmentByClassBacnet(propertyValues, hvacPointDetailList, bd);
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
                log.error("------------------------------>请求设备点位事发生错误,设备{}:{}",RemoteDeviceInfoHolder.REMOTE_DEVICE_ID_MAP.get(k),e.getMessage(), e);
            }

        });
        return result;
    }



    public List<? extends BaseDevice> getMbCurrentData(Integer mbType) {
        List<MbRegisterDetail> mbRegisterDetails = MbRegisterHolder.MASTER_POINT_LIST_MAP.get(mbType);
        Map<String, List<MbRegisterDetail>> mbNodeMap = MbRegisterHolder.MASTER_POINT_MAP.get(mbType);
        Map<String, BatchResults<String>> results  = getBatchResults(mbRegisterDetails);

        List<BaseDevice> result = MbNodeHolder.MODBUS_NODE_MAP.get(mbType);
        result.forEach(bd -> {
            List<MbRegisterDetail> mbRegisterDetailList = mbNodeMap.get(bd.getId());
            HvacUtil.assignmentByClassModbus(results, mbRegisterDetailList, bd);
        });
        return result;
    }

    private Map<String, BatchResults<String>> getBatchResults(List<MbRegisterDetail> mbRegisterDetails) {
        Map<String, BatchResults<String>> result = new HashMap<>(8);

        Map<String, BatchRead<String>> readMap = new HashMap<>(8);
        mbRegisterDetails.forEach(mrd -> {
            readMap.computeIfAbsent(mrd.getMasterId(), k-> new BatchRead<>());
            readMap.get(mrd.getMasterId())
                    .addLocator(mrd.getRegisterId(),
                            BaseLocator.inputRegister(mrd.getSlaveId(), mrd.getOffset(), mrd.getDataType()));
        });
        //查询点位数据
        readMap.forEach((k,v) -> {
            BatchResults<String> results = ModbusUtil.batchRead(MbMasterHolder.MODBUS_MASTER_MAP.get(k), v);
            result.put(k, results);
        });
        return result;
    }


}
