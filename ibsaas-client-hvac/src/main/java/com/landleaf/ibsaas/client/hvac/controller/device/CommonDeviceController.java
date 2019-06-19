package com.landleaf.ibsaas.client.hvac.controller.device;

import com.landleaf.ibsaas.client.hvac.controller.Basic2Controller;
import com.landleaf.ibsaas.client.hvac.service.*;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 15:36
 * @description:
 */
@RestController
@RequestMapping("/common-device")
@AllArgsConstructor
@Slf4j
@Api("设备基础api层")
public class CommonDeviceController extends Basic2Controller {

    private final ICommonDeviceService iCommonDeviceService;

    private final IHvacDeviceService iHvacDeviceService;

    private final IEnergyDataService iEnergyDataService;


    @GetMapping("/reload")
    @ApiOperation("重新加载设备和点位")
    public Response reload(){
        iCommonDeviceService.reload();
        return returnSuccess();
    }

    @GetMapping("/all")
    @ApiOperation("获取所有的硬件设备")
    public Response all(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>CommonDeviceController.all入参为:空");
        List<HvacDevice> hvacDevices = iHvacDeviceService.all();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<CommonDeviceController.all出参为:{}",hvacDevices);
        return returnSuccess(hvacDevices);
    }


    @GetMapping("/current-data/{deviceInstanceNumber}")
    public Response currentData(@PathVariable("deviceInstanceNumber") Integer deviceInstanceNumber){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>CommonDeviceController.currentData入参为:{}",deviceInstanceNumber);
        List<? extends BaseDevice> data = iCommonDeviceService.getCurrentData(deviceInstanceNumber);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<CommonDeviceController.currentData出参为:{}",data);
        return returnSuccess(data);
    }

    @GetMapping("/current-data/redis")
    public Response currentDataToRedis(){
        iCommonDeviceService.currentDataToRedis();
        return returnSuccess();
    }

    @GetMapping("/data-record")
    public Response dataRecord(){
        Date now = new Date();
        List<EnergyData> energyData = iEnergyDataService.dataRecord(now);
        return returnSuccess(energyData);
    }
}
