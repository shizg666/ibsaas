package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.DomesticWaterDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.RainwaterCollectionDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.DomesticWaterVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IDomesticWaterService;
import com.landleaf.ibsaas.web.web.service.hvac.IRainwaterCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:24
 * @description:
 */
@RestController
@RequestMapping("/domestic-water")
@AllArgsConstructor
@Slf4j
@Api("生活水")
public class DomesticWaterWebController extends BasicController {

    private final IDomesticWaterService iDomesticWaterService;

    @GetMapping("/overview")
    @ApiOperation("生活水总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>DomesticWaterWebController.overview入参为:空");
        List<DomesticWaterVO> domesticWaterVOList = iDomesticWaterService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<DomesticWaterWebController.overview出参为:{}",domesticWaterVOList.size());
        return returnSuccess(domesticWaterVOList);
    }

    @PutMapping("/operation")
    @ApiOperation("修改生活水的值")
    public Response operation(@RequestBody DomesticWaterDTO domesticWaterDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>DomesticWaterWebController.operation入参为:{}", domesticWaterDTO);
        if(StringUtils.isBlank(domesticWaterDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iDomesticWaterService.update(domesticWaterDTO);
        return returnSuccess();
    }
}
