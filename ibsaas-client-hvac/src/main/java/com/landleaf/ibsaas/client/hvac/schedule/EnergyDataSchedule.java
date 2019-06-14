package com.landleaf.ibsaas.client.hvac.schedule;

import com.landleaf.ibsaas.client.hvac.service.IEnergyDataElectricService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyDataWaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/14 15:08
 * @description: 能源设备数据抓取
 */
@Component
@EnableScheduling
@Slf4j
public class EnergyDataSchedule {

    @Autowired
    private IEnergyDataElectricService iEnergyDataElectricService;

    @Autowired
    private IEnergyDataWaterService iEnergyDataWaterService;

    @Scheduled(cron = "0 0 * * * *")
    public void toDatabase(){
        Date now = new Date();
        log.info("电表水表数据入库------------------------------>时间为:{}", now);
        iEnergyDataElectricService.dataRecord(now);
        iEnergyDataWaterService.dataRecord(now);
    }

}
