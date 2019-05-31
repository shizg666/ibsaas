package com.landleaf.ibsaas.client.hvac.controller;

import com.landleaf.ibsaas.client.hvac.service.IHvacPointService;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacPointDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Lokiy
 * @date 2019/5/27 16:16
 * @description:
 */
@RestController
@RequestMapping("/hvac-point")
@AllArgsConstructor
@Slf4j
@Api("hvac硬件设备点位类")
public class HvacPointController extends Basic2Controller{

    private final IHvacPointService iHvacPointService;

    @PostMapping
    @ApiOperation("新增远程设备点位")
    public Response save(@RequestBody @ApiParam @Valid HvacPointDTO hvacPointDTO){
        HvacPoint hvacPoint = iHvacPointService.addHvacPoint(hvacPointDTO);
        return returnSuccess(hvacPoint);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个远程硬件点位")
    public Response delete(@PathVariable("id") String id){
        iHvacPointService.deleteById(id);
        return returnSuccess();
    }



    @PutMapping
    @ApiOperation("更新某个远程硬件点位")
    public Response update(@RequestBody @ApiParam HvacPointDTO hvacPointDTO){
        HvacPoint hvacPoint = iHvacPointService.updateById(hvacPointDTO);
        return returnSuccess(hvacPoint);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某个具体远程硬件点位信息")
    public Response get(@PathVariable("id") String id){
        HvacPoint hvacPoint = iHvacPointService.getById(id);
        return returnSuccess(hvacPoint);
    }


}
