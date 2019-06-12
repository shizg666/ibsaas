package com.landleaf.ibsaas.web.web.controller.energy;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping
    @ApiOperation("添加能耗设备")
    public Response save(@RequestBody @ApiParam @Valid EnergyEquipDTO energyEquipDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ConfigSettingController.save入参为:{}", energyEquipDTO);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.addEnergyEquip(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ConfigSettingController.save出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }
}
