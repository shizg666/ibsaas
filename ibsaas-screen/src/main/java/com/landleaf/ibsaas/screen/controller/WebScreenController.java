package com.landleaf.ibsaas.screen.controller;

import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.screen.model.resp.ResponseResult;
import com.landleaf.ibsaas.screen.model.vo.*;
import com.landleaf.ibsaas.screen.service.LargeScreenService;
import com.landleaf.ibsaas.screen.service.ScreenAsyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/10 0010
 * @description:
 */
@RestController
@RequestMapping("/web/screen")
@Api("大屏http各请求")
public class WebScreenController {

    @Autowired
    private LargeScreenService largeScreenService;

    @Autowired
    private ScreenAsyncService screenAsyncService;

    @RequestMapping("/test")
    public Mono<String> test(){
        return Mono.just("Hello World!");
    }

    @ApiOperation("屏幕所有数据")
    @RequestMapping("/total")
    public Mono<ResponseResult> total(){
        Map<String, Object> status = screenAsyncService.asyncExecuteService();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("多参数传感器数据")
    @GetMapping("/sensor")
    public Mono<ResponseResult> sensor(){
        Map<String, SensorVO> status = largeScreenService.sensorStatus();
        return Mono.just(ResponseResult.success(status));
    }


    @ApiOperation("新风机状态数据")
    @GetMapping("/newFan")
    public Mono<ResponseResult> newFan(){
        Map<String, ScreenNewFan> status = largeScreenService.newFanStatus();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("风盘状态数据")
    @GetMapping("/fanCoil")
    public Mono<ResponseResult> fanCoil(){
        ScreenFanCoil status = largeScreenService.fanCoilStatus();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("风盘状态数据")
    @GetMapping("/achpDetail")
    public Mono<ResponseResult> achpDetail(){
        Map<String, List<ScreenAchpDetail>> status = largeScreenService.achpDetailStatus();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("会议预定列表")
    @GetMapping("/meeting")
    public Mono<ResponseResult> meeting(){
        List<LgcMeeting> status = largeScreenService.meetingStatus();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("能耗数据")
    @GetMapping("/energy")
    public Mono<ResponseResult> energy(){
        Object status = largeScreenService.energyStatus();
        return Mono.just(ResponseResult.success(status));
    }

    @ApiOperation("天气数据")
    @GetMapping("/weather")
    public Mono<ResponseResult> weather(){
        ScreenWeather status = largeScreenService.weatherStatus();
        return Mono.just(ResponseResult.success(status));
    }

}
