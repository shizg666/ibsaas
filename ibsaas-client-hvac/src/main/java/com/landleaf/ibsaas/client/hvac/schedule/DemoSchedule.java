package com.landleaf.ibsaas.client.hvac.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author Lokiy
 * @date 2019/2/15
 * @description 定时任务类
 */
@Component
@EnableScheduling
public class DemoSchedule {


//    @Scheduled(cron = "0 0 0 * * *")
    public void test(){
        System.out.println("定时任务执行开始");

        System.out.println("定时任务执行结束");
    }
}
