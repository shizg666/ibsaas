package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.config.HvacNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.HvacPointHolder;
import com.landleaf.ibsaas.client.hvac.config.LocalDeviceConfig;
import com.landleaf.ibsaas.client.hvac.config.RemoteDeviceInfoHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbMasterHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbRegisterHolder;
import com.landleaf.ibsaas.client.hvac.util.BacnetUtil;
import com.landleaf.ibsaas.client.hvac.util.HvacUtil;
import com.landleaf.ibsaas.client.hvac.util.ModbusUtil;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.assist.HvacPointDetail;
import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Async("taskExecutor")
    public void currentOneMbDataToRedis(Integer deviceType){
        List<? extends BaseDevice> currentData = getMbCurrentData(deviceType);
        if (CollectionUtils.isNotEmpty(currentData)) {
            redisHandle.addMap(placeId, String.valueOf(deviceType), currentData);
        }
    }

    @Async("taskExecutor")
    public void currentOneBacnetDataToRedis(Integer deviceType){
        List<? extends BaseDevice> currentData = getCurrentData(deviceType);
        if (CollectionUtils.isNotEmpty(currentData)) {
            redisHandle.addMap(placeId, String.valueOf(deviceType), currentData);
        }
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



    private List<? extends BaseDevice> getMbCurrentData(Integer mbType) {
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
