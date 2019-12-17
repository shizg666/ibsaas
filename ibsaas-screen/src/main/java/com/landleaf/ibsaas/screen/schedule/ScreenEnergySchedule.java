package com.landleaf.ibsaas.screen.schedule;

import com.landleaf.ibsaas.screen.service.ScreenEnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Lokiy
 * @date 2019/12/13 10:51
 * @description:
 */
@Component
@EnableScheduling
@Slf4j
public class ScreenEnergySchedule {

    @Autowired
    private ScreenEnergyService screenEnergyService;

    @Scheduled(cron = "30 0 0 * * *")
    public void dailySumEnergy2Redis(){
        screenEnergyService.lgcSumElectric2Redis();
        screenEnergyService.lgcElectricLineChart2Redis();
        log.info(">>>>>>>>>>>>>>>>>>>>每日电表抄表数据和折线数据刷入redis<<<<<<<<<<<<<<<<<<<<");
    }
}
