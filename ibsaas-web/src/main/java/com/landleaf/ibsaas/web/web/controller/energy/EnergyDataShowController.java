package com.landleaf.ibsaas.web.web.controller.energy;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyDataShowVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowDTO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowQryDTO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyDataShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Lokiy
 * @date 2019/5/27 16:16
 * @description:
 */
@RestController
@RequestMapping("/energy-show")
@AllArgsConstructor
@Slf4j
@Api("页面展示数据")
public class EnergyDataShowController extends BasicController {

    private final IEnergyDataShowService iEnergyDataShowService;

    @PostMapping("add")
    @ApiOperation("新增数据")
    public Response save(@RequestBody @Valid EnergyDataShowDTO request){
        iEnergyDataShowService.addData(request);
        return returnSuccess();
    }

    @GetMapping("list")
    @ApiOperation("查询数据")
    public Response list(EnergyDataShowQryDTO request){
        PageInfo<EnergyDataShowVO> result = iEnergyDataShowService.getList(request);
        return returnSuccess(result);
    }



    @PutMapping("update")
    @ApiOperation("修改数据")
    public Response update(@RequestBody @Valid EnergyDataShowDTO request){
        iEnergyDataShowService.updateById(request);
        return returnSuccess();
    }



}
