package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.config.modbus.MbMasterHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbNodeHolder;
import com.landleaf.ibsaas.client.hvac.config.modbus.MbRegisterHolder;
import com.landleaf.ibsaas.client.hvac.util.HvacUtil;
import com.landleaf.ibsaas.client.hvac.util.ModbusUtil;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
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
    public void currentMbDataToRedis(){
        ModbusDeviceTypeEnum.MAP.entrySet().parallelStream().forEach(entry -> {
            List<? extends BaseDevice> currentData = getMbCurrentData(entry.getKey());
            if (CollectionUtils.isNotEmpty(currentData)) {
                redisHandle.addMap(placeId, String.valueOf(entry.getKey()), currentData);
            }
        });
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
