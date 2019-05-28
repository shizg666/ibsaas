package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacFieldDTO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacFieldService;
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
@RequestMapping("/hvac-field")
@AllArgsConstructor
@Slf4j
@Api("hvac硬件字段控制类")
public class HvacFieldController extends BasicController {

    private final IHvacFieldService iHvacFieldService;

    @PostMapping
    @ApiOperation("新增远程设备字段")
    public Response save(@RequestBody @ApiParam @Valid HvacFieldDTO hvacFieldDTO){
        HvacField hvacField = iHvacFieldService.addHvacField(hvacFieldDTO);
        return returnSuccess(hvacField);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个远程硬件字段")
    public Response delete(@PathVariable("id") String id){
        iHvacFieldService.deleteById(id);
        return returnSuccess();
    }



    @PutMapping
    @ApiOperation("更新某个远程硬件字段")
    public Response update(@RequestBody @ApiParam HvacFieldDTO hvacFieldDTO){
        HvacField hvacField = iHvacFieldService.updateById(hvacFieldDTO);
        return returnSuccess(hvacField);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某个具体远程硬件字段信息")
    public Response get(@PathVariable("id") String id){
        HvacField hvacField = iHvacFieldService.getById(id);
        return returnSuccess(hvacField);
    }


}
