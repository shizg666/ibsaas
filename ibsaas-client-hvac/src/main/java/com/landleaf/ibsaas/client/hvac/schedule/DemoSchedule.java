package com.landleaf.ibsaas.client.hvac.schedule;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Lokiy
 * @date 2019/2/15
 * @description 定时任务类
 *
 *
 *
 */
@Component
@EnableScheduling
@Slf4j
public class DemoSchedule {

    @Autowired
    private ICommonDeviceService iCommonDeviceService;

//    @Scheduled(cron = "* * * * * *")
    public void test(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>开始刷入redis数据<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>开始刷入redis数据<<<<<<<<<<<<<<<<<<<<<<<<<");
        long start = System.currentTimeMillis();
        iCommonDeviceService.currentDataToRedis();
        long t = System.currentTimeMillis() - start;
        System.out.println("------------------------------>时间为"+t+"毫秒");
        log.info("------------------------------>时间为{}毫秒", t);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>刷入redis数据结束<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>刷入redis数据结束<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
