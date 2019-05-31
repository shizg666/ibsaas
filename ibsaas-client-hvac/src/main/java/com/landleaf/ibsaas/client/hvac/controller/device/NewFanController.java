package com.landleaf.ibsaas.client.hvac.controller.device;

import com.landleaf.ibsaas.client.hvac.controller.Basic2Controller;
import com.landleaf.ibsaas.client.hvac.service.INewFanService;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.enums.hvac.newfan.NewFanRunningModeEnum;

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
 * @date 2019/5/28 16:10
 * @description: 四效新风控制类
 */
@RestController
@RequestMapping("/new-fan")
@AllArgsConstructor
@Slf4j
@Api("四效新风")
public class NewFanController extends Basic2Controller {

    private final INewFanService iNewFanService;


    @GetMapping("/overview")
    @ApiOperation("四效新风机总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.overview入参为:空");
        List<NewFanVO> newFanVOList = iNewFanService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<NewFanController.overview出参为:{}",newFanVOList);
        return returnSuccess(newFanVOList);
    }


    @GetMapping("/running-modes")
    @ApiOperation("获取运行模式下拉菜单")
    public Response runningModes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.runningModes入参为:空");
        List<ChoiceButton> choiceButtons = NewFanRunningModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<NewFanController.runningModes出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("单个四效新风机当前状态")
    public Response getInfo(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.get入参为:{}", id);
        NewFanVO newFanVO = iNewFanService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<NewFanController.get出参为:{}",newFanVO);
        return returnSuccess(newFanVO);
    }

    @GetMapping("/operation/{field}/{value}")
    @ApiOperation("修改四效新风的值")
    public Response operation(@PathVariable("field") String field, @PathVariable("value") String value){
        //TODO
        return returnSuccess();
    }
}
