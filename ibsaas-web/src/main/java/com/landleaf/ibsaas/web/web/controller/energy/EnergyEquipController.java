package com.landleaf.ibsaas.web.web.controller.energy;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.BasePageVO;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipSearchDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.get入参为:{}", id);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.getEnergyEquipById(id);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.get出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }

    @PostMapping("/list")
    @ApiOperation("能耗设备查询列表")
    public Response list(@RequestBody EnergyEquipSearchDTO energyEquipSearchDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.list入参为:{}", energyEquipSearchDTO);
        PageInfo<EnergyEquipSearchVO> pageInfo = iEnergyEquipService.list(energyEquipSearchDTO);
        BasePageVO<EnergyEquipSearchVO> result = new BasePageVO<>(pageInfo.getList(), pageInfo.getTotal());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.list出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/data-list")
    @ApiOperation("能耗设备抄表数据查询")
    public Response dataList(@RequestBody EnergyEquipSearchDTO energyEquipSearchDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.dataList 入参为:{}", energyEquipSearchDTO);
        PageInfo<EnergyEquipSearchVO> pageInfo = iEnergyEquipService.dataList(energyEquipSearchDTO);
        BasePageVO<EnergyEquipSearchVO> result = new BasePageVO<>(pageInfo.getList(), pageInfo.getTotal());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.dataList 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/current-data-list")
    @ApiOperation("能耗设备实时表显")
    public Response currentDataList(@RequestBody EnergyEquipSearchDTO energyEquipSearchDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.currentDataList 入参为:{}", energyEquipSearchDTO);
        PageInfo<EnergyEquipSearchVO> pageInfo = iEnergyEquipService.currentDataList(energyEquipSearchDTO);
        BasePageVO<EnergyEquipSearchVO> result = new BasePageVO<>(pageInfo.getList(), pageInfo.getTotal());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.currentDataList 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping
    @ApiOperation("添加能耗设备")
    public Response save(@RequestBody @ApiParam @Valid EnergyEquipDTO energyEquipDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.save入参为:{}", energyEquipDTO);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.addEnergyEquip(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.save出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }


    @PutMapping
    @ApiOperation("根据id更新能耗设备")
    public Response update(@RequestBody @ApiParam @Valid EnergyEquipDTO energyEquipDTO){
        if(StringUtils.isBlank(energyEquipDTO.getId())){
            throw new BusinessException("更改的能耗设备的id为空");
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.update入参为:{}", energyEquipDTO);
        EnergyEquipVO energyEquipVO = iEnergyEquipService.updateEnergyEquipById(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.update出参为:{}", energyEquipVO);
        return returnSuccess(energyEquipVO);
    }


    @PutMapping("/verify")
    @ApiOperation("更新能耗设备校验时间和校验值")
    public Response updateVerify(@RequestBody @ApiParam EnergyEquipDTO energyEquipDTO){
        if(StringUtils.isBlank(energyEquipDTO.getId())){
            throw new BusinessException("更改的能耗设备的id为空");
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.updateVerify入参为:{}", energyEquipDTO);
        boolean result = iEnergyEquipService.updateEnergyEquipVerifyById(energyEquipDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.updateVerify出参为:{}", result);
        return returnSuccess(result);
    }




    @GetMapping("/nodes")
    @ApiOperation("获取所有电表和水表节点")
    public Response nodes(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyEquipController.nodes入参为:空");
        NodeChoiceVO nodeChoiceVO = iEnergyEquipService.nodes();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyEquipController.nodes出参为:{}", nodeChoiceVO);
        return returnSuccess(nodeChoiceVO);
    }
}
