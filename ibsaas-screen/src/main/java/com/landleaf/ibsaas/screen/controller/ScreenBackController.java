package com.landleaf.ibsaas.screen.controller;

import com.landleaf.ibsaas.screen.service.WeatherInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Lokiy
 * @date 2019/12/13 15:40
 * @description:
 */
@RestController
@RequestMapping("/web/back/screen")
@Api("大屏http各请求")
public class ScreenBackController {

    @Autowired
    private WeatherInfoService weatherInfoService;

    @RequestMapping("/refresh/lgc-weather")
    public Mono<String> lgcWeatherRefresh(){
        weatherInfoService.lgcWeather2Redis();
        return Mono.just("SUCCESS");
    }
}
