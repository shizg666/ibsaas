package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacDeviceDTO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacDeviceService;
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
@RequestMapping("/hvac-device")
@AllArgsConstructor
@Slf4j
@Api("hvac硬件设备控制类")
public class HvacDeviceController extends BasicController {

    private final IHvacDeviceService iHvacDeviceService;

    @PostMapping
    @ApiOperation("新增远程设备")
    public Response save(@RequestBody @ApiParam @Valid HvacDeviceDTO hvacDeviceDTO){
        HvacDevice hvacDevice = iHvacDeviceService.addHvacDevice(hvacDeviceDTO);
        return returnSuccess(hvacDevice);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个远程硬件")
    public Response delete(@PathVariable("id") String id){
        iHvacDeviceService.deleteById(id);
        return returnSuccess();
    }



    @PutMapping
    @ApiOperation("更新某个远程硬件")
    public Response update(@RequestBody @ApiParam HvacDeviceDTO hvacDeviceDTO){
        HvacDevice hvacDevice = iHvacDeviceService.updateById(hvacDeviceDTO);
        return returnSuccess(hvacDevice);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某个具体远程硬件的信息")
    public Response get(@PathVariable("id") String id){
        HvacDevice hvacDevice = iHvacDeviceService.getById(id);
        return returnSuccess(hvacDevice);
    }


}
