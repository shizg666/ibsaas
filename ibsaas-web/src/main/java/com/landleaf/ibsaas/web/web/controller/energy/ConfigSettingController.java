package com.landleaf.ibsaas.web.web.controller.energy;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.ConfigSettingDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/12 15:03
 * @description:
 */
@RestController
@RequestMapping("config-setting")
@AllArgsConstructor
@Slf4j
@Api("配置信息控制类")
public class ConfigSettingController extends BasicController {

    private final IConfigSettingService iConfigSettingService;

    @PostMapping
    @ApiOperation("新增某条配置")
    public Response save(@RequestBody @ApiParam @Valid ConfigSettingDTO configSettingDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.add入参为:{}", configSettingDTO);
        ConfigSetting configSetting =  iConfigSettingService.add(configSettingDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.add出参为:{}",configSetting);
        return returnSuccess(configSetting);
    }

    @PutMapping
    @ApiOperation("修改某条配置")
    public Response update(@RequestBody ConfigSettingDTO configSettingDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.update入参为:{}", configSettingDTO);
        ConfigSetting configSetting =  iConfigSettingService.updateById(configSettingDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.update出参为:{}",configSetting);
        return returnSuccess(configSetting);
    }

    @GetMapping("type-list/{type}")
    @ApiOperation("根据配置的type类型获取所有的type配置")
    public Response typeList(@PathVariable("type") String type){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.typeList入参为:{}", type);
        List<ConfigSettingVO> configSettingVOList = iConfigSettingService.typeList(type);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.typeList出参为:{}",configSettingVOList);
        return returnSuccess(configSettingVOList);
    }

    @GetMapping("type-one/{type}/{code}")
    @ApiOperation("根据配置的type类型和code获取具体的配置")
    public Response typeOne(@PathVariable("type") String type, @PathVariable("code") String code){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.typeOne入参为:{},{}", type, code);
        ConfigSettingVO configSettingVO = iConfigSettingService.typeOne(type, code);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.typeOne出参为:{}",configSettingVO);
        return returnSuccess(configSettingVO);
    }


    /**
     * 以下为下拉菜单
     */
    @GetMapping("choice-button/{type}")
    @ApiOperation("通用下拉菜单")
    public Response choiceButton(@PathVariable("type") String type){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.choiceButton入参为:空");
        List<ChoiceButton> choiceButtons = iConfigSettingService.getChoiceButtons(type);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.choiceButton出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("choice-button/equip-type")
    @ApiOperation("设备类型下拉菜单")
    public Response equipType(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.equipType入参为:空");
        List<ChoiceButton> choiceButtons = iConfigSettingService.getChoiceButtons("equip_type");
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.equipType出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }


    @GetMapping("choice-button/equip-floor")
    @ApiOperation("楼层下拉菜单")
    public Response equipFloor(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.equipFloor入参为:空");
        List<ChoiceButton> choiceButtons = iConfigSettingService.getChoiceButtons("equip_floor");
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.equipFloor出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("choice-button/equip-area")
    @ApiOperation("设备所属区域下拉菜单")
    public Response equipArea(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.equipArea入参为:空");
        List<ChoiceButton> choiceButtons = iConfigSettingService.getChoiceButtons("equip_area");
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.equipArea出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }

    @GetMapping("choice-button/equip-classification")
    @ApiOperation("设备所属分项下拉菜单")
    public Response equipClassification(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.equipClassification入参为:空");
//        List<ChoiceButton> choiceButtons = iConfigSettingService.getChoiceButtons("equip_classification");
        Map<String, List<ChoiceButton>> choiceButtons = iConfigSettingService.getEquipClassificationChoiceButton("equip_classification");
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.equipClassification出参为:{}",choiceButtons);
        return returnSuccess(choiceButtons);
    }















}
