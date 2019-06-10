package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
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
        List<FanCoilVO> fanCoilVOList = iFanCoilWebService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.overview出参为:{}",fanCoilVOList);
        return returnSuccess(fanCoilVOList);
    }


    @GetMapping("/running-modes")
    @ApiOperation("获取运行模式下拉菜单")
    public Response runningModes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>FanCoilWebController.runningModes入参为:空");
        List<ChoiceButton> choiceButtons = NewFanRunningModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<FanCoilWebController.runningModes出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
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
}
