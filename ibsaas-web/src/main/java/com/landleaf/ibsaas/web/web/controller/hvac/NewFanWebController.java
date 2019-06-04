package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.enums.hvac.newfan.NewFanRunningModeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.INewFanWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/3 9:49
 * @description:
 */
@RestController
@RequestMapping("/new-fan")
@AllArgsConstructor
@Slf4j
@Api("四效新风")
public class NewFanWebController extends BasicController {

    private final INewFanWebService iNewFanWebService;


    @GetMapping("/overview")
    @ApiOperation("四效新风机总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.overview入参为:空");
        List<NewFanVO> newFanVOList = iNewFanWebService.overview();
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
        NewFanVO newFanVO = iNewFanWebService.getInfoById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<NewFanController.get出参为:{}",newFanVO);
        return returnSuccess(newFanVO);
    }

    @PutMapping("/operation")
    @ApiOperation("修改四效新风的值")
    public Response operation(@RequestBody NewFanDTO newFanDTO){
        if(StringUtils.isBlank(newFanDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iNewFanWebService.update(newFanDTO);
        return returnSuccess();
    }


}
