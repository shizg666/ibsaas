package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpDetailDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpMonitorDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpPumpValveDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpDetailVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpMonitorVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpPumpValveVO;
import com.landleaf.ibsaas.common.enums.hvac.achp.AchpDetailContSettingEnum;
import com.landleaf.ibsaas.common.enums.hvac.achp.AchpDetailOnOffEnum;
import com.landleaf.ibsaas.common.enums.hvac.achp.AchpDetailRunningModeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IAchpDetailWebService;
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
 * @date 2019/7/4 9:58
 * @description: 风冷热泵详参接口
 */
@RestController
@RequestMapping("/achp")
@AllArgsConstructor
@Slf4j
@Api("风冷热泵接口")
public class AchpWebController extends BasicController {

    private final IAchpDetailWebService iAchpDetailWebService;


    /*
        风冷热泵详参接口
     */

    @GetMapping("/detail/overview")
    @ApiOperation("风冷热泵详参总览")
    public Response overviewDetail(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.overviewDetail:空");
//        List<AchpDetailVO> achpDetailVOList = iAchpDetailWebService.overview();
        Map<String, List<AchpDetailVO>> achpDetailVOList = iAchpDetailWebService.totalOverviewDetail();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.overviewDetail:{}",achpDetailVOList.size());
        return returnSuccess(achpDetailVOList);
    }


    @PutMapping("/detail/operation")
    @ApiOperation("修改风冷热泵详参的值")
    public Response operationDetail(@RequestBody AchpDetailDTO achpDetailDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.operationDetail:{}", achpDetailDTO);
        if(StringUtils.isBlank(achpDetailDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iAchpDetailWebService.updateDetail(achpDetailDTO);
        return returnSuccess();
    }

    @GetMapping("/detail/on-off")
    @ApiOperation("风冷热泵详参启停控制")
    public Response onOffDetail(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.onOffDetail 入参为:空");
        List<ChoiceButton> choiceButtons = AchpDetailOnOffEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.onOffDetail 出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    @GetMapping("/detail/running-mode")
    @ApiOperation("风冷热泵详参运行模式设定")
    public Response runningModeDetail(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.runningModeDetail:空");
        List<ChoiceButton> choiceButtons = AchpDetailRunningModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.runningModeDetail:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    @GetMapping("/detail/cont-setting")
    @ApiOperation("风冷热泵详参制冷制热控制选择值设定")
    public Response contSettingDetail(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.contSettingDetail:空");
        List<ChoiceButton> choiceButtons = AchpDetailContSettingEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.contSettingDetail:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    /*
        风冷热泵-水泵水阀
     */

    @GetMapping("/pump-valve/overview")
    @ApiOperation("风冷热泵水泵水阀总览")
    public Response overviewPumpValve(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.overviewPumpValve:空");
        List<AchpPumpValveVO> achpPumpValveVOList = iAchpDetailWebService.overviewPumpValve();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.overviewPumpValve:{}",achpPumpValveVOList.size());
        return returnSuccess(achpPumpValveVOList);
    }


    @PutMapping("/pump-valve/operation")
    @ApiOperation("修改风冷热泵水泵水阀的值")
    public Response operationPumpValve(@RequestBody AchpPumpValveDTO achpPumpValveDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.operationPumpValve:{}", achpPumpValveDTO);
        if(StringUtils.isBlank(achpPumpValveDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iAchpDetailWebService.updatePumpValve(achpPumpValveDTO);
        return returnSuccess();
    }



    /*
        风冷热泵-监测
     */



    @GetMapping("/monitor/overview")
    @ApiOperation("风冷热泵监测总览")
    public Response overviewMonitor(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.overviewMonitor:空");
        List<AchpMonitorVO> achpMonitorVOList = iAchpDetailWebService.overviewMonitor();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AchpWebController.overviewMonitor:{}",achpMonitorVOList.size());
        return returnSuccess(achpMonitorVOList);
    }


    @PutMapping("/monitor/operation")
    @ApiOperation("修改风冷热泵监测的值")
    public Response operationMonitor(@RequestBody AchpMonitorDTO achpMonitorDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AchpWebController.operationMonitor:{}", achpMonitorDTO);
        if(StringUtils.isBlank(achpMonitorDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iAchpDetailWebService.updateMonitor(achpMonitorDTO);
        return returnSuccess();
    }


}
