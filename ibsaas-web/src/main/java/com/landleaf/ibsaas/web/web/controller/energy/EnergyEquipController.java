package com.landleaf.ibsaas.web.web.controller.energy;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Lokiy
 * @date 2019/6/12 17:37
 * @description:
 */
@RestController
@RequestMapping("energy-equip")
@AllArgsConstructor
@Slf4j
@Api("能耗设备控制类")
public class EnergyEquipController extends BasicController {

    private final IEnergyEquipService iEnergyEquipService;


    @GetMapping("/info/{id}")
    @ApiOperation("添加能耗设备详情")
    public Response get(@PathVariable("id") String id){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.get入参为:{}", id);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.getEnergyEquipById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.get出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }

//    @GetMapping("/list")
//    @ApiOperation("添加能耗设备详情")
//    public Response get(@RequestBody Object o){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.get入参为:{}", id);
//        EnergyEquipVO energyEquipVO = iEnergyEquipService.get(id);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.get出参为:{}", energyEquipVO);
//        return returnSuccess(energyEquipVO);
//    }


    @PostMapping
    @ApiOperation("添加能耗设备")
    public Response save(@RequestBody @ApiParam @Valid EnergyEquipDTO energyEquipDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.save入参为:{}", energyEquipDTO);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.addEnergyEquip(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.save出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }


    @PutMapping
    @ApiOperation("添加能耗设备")
    public Response update(@RequestBody @ApiParam @Valid EnergyEquipDTO energyEquipDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.update入参为:{}", energyEquipDTO);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.updateEnergyEquipById(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.update出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }



    @GetMapping("/nodes")
    @ApiOperation("获取所有电表和水表节点")
    public Response nodes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.nodes入参为:空");
        NodeChoiceVO nodeChoiceVO = iEnergyEquipService.nodes();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.nodes出参为:{}", nodeChoiceVO);
        return returnSuccess(nodeChoiceVO);
    }
}
