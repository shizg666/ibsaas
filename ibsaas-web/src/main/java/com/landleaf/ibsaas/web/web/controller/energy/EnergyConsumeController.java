package com.landleaf.ibsaas.web.web.controller.energy;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.service.energy.IEnergyConsumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/energy-consume")
@Api(value = "/energy-consume", description = "能耗曲线操作")
public class EnergyConsumeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyConsumeController.class);

    @Autowired
    private IEnergyConsumeService energyConsumeService;

    @GetMapping("/flow")
    @ApiOperation("分区/分项能耗")
    public Response energyFlow(@ApiParam(value = "数据类型（电:1;水:2）", required = false) @RequestParam(value = "dataType", required = false, defaultValue = "2") Integer dataType,
                               @ApiParam(value = "起始时间 （格式：yyyy-MM-dd HH:mm:ss）", required = true) @RequestParam("startTime") String startTime,
                               @ApiParam(value = "截止时间 （格式：yyyy-MM-dd HH:mm:ss）", required = true) @RequestParam("endTime") String endTime,
                               @ApiParam(value = "查询类型 （分区:1;分项:2）", required = true) @RequestParam("queryType") Integer queryType,
                               @ApiParam(value = "查询类型值 （1F/照明）", required = false) @RequestParam("queryTypeValue") String queryTypeValue,
                               @ApiParam(value = "维度 （年:1;月:2;日:3;时:4）", required = true) @RequestParam("dimensionType") Integer dimensionType) {
        LOGGER.info("曲线操作接口请求参数：==》%s", String.format("查询类型：%s;查询类型值:%s;数据类型:%s;维度:%s;起止时间：%s--%s", queryType, queryTypeValue,
                dataType, dimensionType, startTime, endTime));

        Map<String,Map<String,List<String>>> queryResult=energyConsumeService.energyFlow(queryType,queryTypeValue,dimensionType,dataType,startTime,endTime);

        return null;
    }
}
