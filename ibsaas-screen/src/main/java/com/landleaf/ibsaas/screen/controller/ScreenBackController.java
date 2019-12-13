package com.landleaf.ibsaas.screen.controller;

import com.landleaf.ibsaas.screen.model.vo.ScreenElectric;
import com.landleaf.ibsaas.screen.service.LargeScreenService;
import com.landleaf.ibsaas.screen.service.ScreenEnergyService;
import com.landleaf.ibsaas.screen.service.WeatherInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    @Autowired
    private ScreenEnergyService screenEnergyService;

    @Autowired
    private LargeScreenService largeScreenService;

    @GetMapping("/refresh/lgc-weather")
    public Mono<String> lgcWeatherRefresh(){
        weatherInfoService.lgcWeather2Redis();
        return Mono.just("SUCCESS");
    }

    @GetMapping("/refresh/lgc-electric")
    public Mono<String> lgcElectricRefresh(){
        screenEnergyService.lgcSumElectric2Redis();
        return Mono.just("SUCCESS");
    }



    @GetMapping(value = "/list/electric", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ScreenElectric> electricList(){
        return Flux.interval(Duration.ofSeconds(1)).map(l -> largeScreenService.energyStatus());
    }
}
