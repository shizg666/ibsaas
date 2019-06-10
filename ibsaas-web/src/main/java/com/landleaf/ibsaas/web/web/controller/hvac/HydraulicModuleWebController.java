package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.vo.HydraulicModuleVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IHydraulicModuleWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/10 13:30
 * @description:
 */
@RestController
@RequestMapping("/hydraulic-module")
@AllArgsConstructor
@Slf4j
@Api("水力模块")
public class HydraulicModuleWebController extends BasicController {

    private IHydraulicModuleWebService iHydraulicModuleWebService;


    @GetMapping("/overview")
    @ApiOperation("四效新风机总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>HydraulicModuleWebController.overview入参为:空");
        List<HydraulicModuleVO> hydraulicModuleVOList = iHydraulicModuleWebService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<HydraulicModuleWebController.overview出参为:{}",hydraulicModuleVOList);
        return returnSuccess(hydraulicModuleVOList);
    }


    @GetMapping("/info/{id}")
    @ApiOperation("单个四效新风机当前状态")
    public Response getInfo(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>HydraulicModuleWebController.get入参为:{}", id);
        HydraulicModuleVO hydraulicModuleVO = iHydraulicModuleWebService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<HydraulicModuleWebController.get出参为:{}",hydraulicModuleVO);
        return returnSuccess(hydraulicModuleVO);
    }

    @PutMapping("/operation")
    @ApiOperation("修改四效新风的值")
    public Response operation(@RequestBody HydraulicModuleVO hydraulicModuleVO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.operation入参为:{}", hydraulicModuleVO);
        if(StringUtils.isBlank(hydraulicModuleVO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iHydraulicModuleWebService.update(hydraulicModuleVO);
        return returnSuccess();
    }
}
