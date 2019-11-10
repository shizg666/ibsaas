package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.vo.WeatherStationVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IWeatherStationWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/6 16:27
 * @description:
 */
@RestController
@RequestMapping("/weather-station")
@AllArgsConstructor
@Slf4j
@Api("气象站")
public class WeatherStationWebController extends BasicController {

    private final IWeatherStationWebService iWeatherStationWebService;

    @GetMapping("/overview")
    @ApiOperation("气象站总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>WeatherStationWebController.overview入参为:空");
        List<WeatherStationVO> weatherStationVOList = iWeatherStationWebService.overview();
        WeatherStationVO weatherStationVO = weatherStationVOList.get(0);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<WeatherStationWebController.overview出参为:{}",weatherStationVO);
        return returnSuccess(weatherStationVO);
    }



    @GetMapping("/info/{id}")
    @ApiOperation("单个气象站当前状态")
    public Response getInfo(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>WeatherStationWebController.get入参为:{}", id);
        WeatherStationVO weatherStationVO = iWeatherStationWebService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<WeatherStationWebController.get出参为:{}",weatherStationVO);
        return returnSuccess(weatherStationVO);
    }
}
