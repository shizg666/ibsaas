package com.landleaf.ibsaas.client.hvac.schedule;

import com.landleaf.ibsaas.client.hvac.service.IEnergyDataElectricService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyDataWaterService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyEquipDataCliService;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private IEnergyEquipDataCliService iEnergyEquipDataService;

    @Scheduled(cron = "0 0 * * * *")
    public void toDatabase(){
        //系统调用产生的秒数误差
        Date now = new Date(System.currentTimeMillis()/IbsaasConstant.SECOND_OFFSET*IbsaasConstant.SECOND_OFFSET);

        log.info("电表水表数据入库------------------------------>时间为:{}", now);
        List<EnergyDataElectric> energyDataElectrics = iEnergyDataElectricService.dataRecord(now);
        List<EnergyDataWater> energyDataWaters = iEnergyDataWaterService.dataRecord(now);
        List<EnergyEquipData> energyEquipDatas = iEnergyEquipDataService.dateRecord(now, energyDataElectrics, energyDataWaters);
    }

}
