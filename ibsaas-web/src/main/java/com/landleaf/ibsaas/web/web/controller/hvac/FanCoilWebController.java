package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
import com.landleaf.ibsaas.common.enums.hvac.fancoil.*;
import com.landleaf.ibsaas.common.enums.hvac.newfan.NewFanRunningModeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IFanCoilWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/10 14:32
 * @description:
 */
@RestController
@RequestMapping("/fan-coil")
@AllArgsConstructor
@Slf4j
@Api("风机盘管")
public class FanCoilWebController extends BasicController {

    private final IFanCoilWebService iFanCoilWebService;


    @GetMapping("/overview")
    @ApiOperation("风机盘管总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.overview入参为:空");
//        List<FanCoilVO> fanCoilVOList = iFanCoilWebService.overview();
        Map<String, Map<String, FanCoilVO>> fanCoilVOList = iFanCoilWebService.totalOverView();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.overview出参为:{}",fanCoilVOList.size());
        return returnSuccess(fanCoilVOList);
    }


    @GetMapping("/info/{id}")
    @ApiOperation("单个风机盘管当前状态")
    public Response getInfo(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.get入参为:{}", id);
        FanCoilVO fanCoilVO = iFanCoilWebService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.get出参为:{}",fanCoilVO);
        return returnSuccess(fanCoilVO);
    }

    @PutMapping("/operation")
    @ApiOperation("修改风机盘管的值")
    public Response operation(@RequestBody FanCoilDTO fanCoilDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.operation入参为:{}", fanCoilDTO);
        if(StringUtils.isBlank(fanCoilDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iFanCoilWebService.update(fanCoilDTO);
        return returnSuccess();
    }


    @GetMapping("/on-off")
    @ApiOperation("风盘开关机状态")
    public Response onOff(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.onOff入参为:空");
        List<ChoiceButton> choiceButtons = FanCoilOnOffEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.onOffs出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    @GetMapping("/running-modes-1")
    @ApiOperation("1楼风盘运行模式")
    public Response runningModesEx(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.runningModesEx入参为:空");
        List<ChoiceButton> choiceButtons = FanCoilRunningModeExEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.runningModesEx出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("/running-modes")
    @ApiOperation("2-4楼风盘运行模式")
    public Response runningModes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.runningModes入参为:空");
        List<ChoiceButton> choiceButtons = FanCoilRunningModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.runningModes出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("/mach-modes-1")
    @ApiOperation("1楼风盘风机模式")
    public Response machModesEx(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.machModesEx入参为:空");
        List<ChoiceButton> choiceButtons = FanCoilMachModeExEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.machModesEx出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("/mach-modes")
    @ApiOperation("2-4楼风盘风机模式")
    public Response machModes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.machModes入参为:空");
        List<ChoiceButton> choiceButtons = FanCoilMachModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.machModes出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }
}
