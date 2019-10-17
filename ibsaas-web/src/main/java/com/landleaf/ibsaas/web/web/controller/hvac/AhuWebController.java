package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.AhuDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AhuVO;
import com.landleaf.ibsaas.common.enums.hvac.ahu.AhuHandAutomaticallyEnum;
import com.landleaf.ibsaas.common.enums.hvac.ahu.AhuSeasonModeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IAhuWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/4 9:58
 * @description: Ahu控制类
 */
@RestController
@RequestMapping("/ahu")
@AllArgsConstructor
@Slf4j
@Api("AHU")
public class AhuWebController extends BasicController {

    private final IAhuWebService iAhuWebService;

    @GetMapping("/overview")
    @ApiOperation("ahu总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AhuWebController.overview入参为:空");
        List<AhuVO> ahuVOList = iAhuWebService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AhuWebController.overview出参为:{}",ahuVOList.size());
        return returnSuccess(ahuVOList);
    }


    @PutMapping("/operation")
    @ApiOperation("修改ahu的值")
    public Response operation(@RequestBody AhuDTO ahuDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AhuWebController.operation入参为:{}", ahuDTO);
        if(StringUtils.isBlank(ahuDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iAhuWebService.update(ahuDTO);
        return returnSuccess();
    }

    @GetMapping("/season-mode")
    @ApiOperation("AHU季节模式")
    public Response seasonMode(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AhuWebController.seasonMode 入参为:空");
        List<ChoiceButton> choiceButtons = AhuSeasonModeEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AhuWebController.seasonMode 出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    @GetMapping("/hand-auto")
    @ApiOperation("1楼风盘运行模式")
    public Response handAuto(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>AhuWebController.handAuto:空");
        List<ChoiceButton> choiceButtons = AhuHandAutomaticallyEnum.getChoiceButtons();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<AhuWebController.handAuto:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


}
