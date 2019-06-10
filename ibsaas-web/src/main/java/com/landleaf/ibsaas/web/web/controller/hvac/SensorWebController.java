package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.ISensorWebService;
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
 * @date 2019/6/10 13:58
 * @description:
 */
@RestController
@RequestMapping("/sensor")
@AllArgsConstructor
@Slf4j
@Api("传感器")
public class SensorWebController extends BasicController {

    private final ISensorWebService iSensorWebService;

    @GetMapping("/overview")
    @ApiOperation("传感器总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>SensorWebController.overview入参为:空");
        List<SensorVO> sensorVOList = iSensorWebService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<SensorWebController.overview出参为:{}",sensorVOList);
        return returnSuccess(sensorVOList);
    }



    @GetMapping("/info/{id}")
    @ApiOperation("单个传感器当前状态")
    public Response getInfo(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>SensorWebController.get入参为:{}", id);
        SensorVO sensorVO = iSensorWebService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<SensorWebController.get出参为:{}",sensorVO);
        return returnSuccess(sensorVO);
    }
}
