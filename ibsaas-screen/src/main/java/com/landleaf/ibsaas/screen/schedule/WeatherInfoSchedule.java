package com.landleaf.ibsaas.screen.schedule;

import com.landleaf.ibsaas.screen.service.WeatherInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Lokiy
 * @date 2019/12/12 19:04
 * @description:
 */
@Component
@EnableScheduling
@Slf4j
public class WeatherInfoSchedule {

    @Autowired
    private WeatherInfoService weatherInfoService;


//    @Scheduled(cron = "0 0/5 * * * *")
    public void shanghaiWeather(){
        weatherInfoService.weather2Redis("上海");
    }



    @Scheduled(cron = "0 0/30 * * * *")
    public void lgcWeather(){
        weatherInfoService.lgcWeather2Redis();
    }
}
