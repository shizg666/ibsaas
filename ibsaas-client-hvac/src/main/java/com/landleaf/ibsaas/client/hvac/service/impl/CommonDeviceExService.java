package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author Lokiy
 * @date 2019/7/11 17:37
 * @description:
 */
@Service
@Slf4j
public class CommonDeviceExService {

    @Autowired
    private CommonAsyncService commonAsyncService;

    @Async("taskExecutor")
    public void currentMbDataToRedis(){
        ModbusDeviceTypeEnum.MAP.forEach((key, value) -> commonAsyncService.currentOneMbDataToRedis(key));
    }

    @Async("taskExecutor")
    public void currentBacnetDataToRedis(){
        BacnetDeviceTypeEnum.MAP.forEach((key, value) -> commonAsyncService.currentOneBacnetDataToRedis(key));
    }





    @Async("taskExecutor")
    public void currentBacnetDataToDatabase(Date date) {
        ModbusDeviceTypeEnum.MAP.forEach((key, value) -> commonAsyncService.currentOneMbDataToDatabase(key, date));
    }

    @Async("taskExecutor")
    public void currentMbDataToDatabase(Date date) {
        BacnetDeviceTypeEnum.MAP.forEach((key, value) -> commonAsyncService.currentOneBacnetDataToDatabase(key, date));
    }
}
