package com.landleaf.ibsaas.client.hvac.controller.device;

import com.landleaf.ibsaas.client.hvac.controller.Basic2Controller;
import com.landleaf.ibsaas.client.hvac.service.INewFanService;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.enums.hvac.newfan.NewFanRunningModeEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/running-modes")
    @ApiOperation("获取运行模式下拉菜单")
    public Response runningModes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>NewFanController.runningModes入参为:空");
        List<ChoiceButton> choiceButtons = NewFanRunningModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<NewFanController.runningModes出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @PutMapping("/operation")
    @ApiOperation("修改四效新风的值")
    public Response operation(@RequestBody NewFanDTO newFanDTO){
        iNewFanService.update(newFanDTO);
        return returnSuccess();
    }
}
