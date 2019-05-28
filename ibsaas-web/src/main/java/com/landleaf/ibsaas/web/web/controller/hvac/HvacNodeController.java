package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacNodeDTO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacNodeService;
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
@RequestMapping("/hvac-node")
@AllArgsConstructor
@Slf4j
@Api("hvac硬件设备节点类")
public class HvacNodeController extends BasicController {

    private final IHvacNodeService iHvacNodeService;

    @PostMapping
    @ApiOperation("新增远程设备节点")
    public Response save(@RequestBody @ApiParam @Valid HvacNodeDTO hvacNodeDTO){
        HvacNode hvacNode = iHvacNodeService.addHvacNode(hvacNodeDTO);
        return returnSuccess(hvacNode);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个远程硬件节点")
    public Response delete(@PathVariable("id") String id){
        iHvacNodeService.deleteById(id);
        return returnSuccess();
    }



    @PutMapping
    @ApiOperation("更新某个远程硬件节点")
    public Response update(@RequestBody @ApiParam HvacNodeDTO hvacNodeDTO){
        HvacNode hvacNode = iHvacNodeService.updateById(hvacNodeDTO);
        return returnSuccess(hvacNode);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某个具体远程硬件的节点信息")
    public Response get(@PathVariable("id") String id){
        HvacNode hvacNode = iHvacNodeService.getById(id);
        return returnSuccess(hvacNode);
    }


}
