package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.RainwaterCollectionDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
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
@RequestMapping("/rainwater-collection")
@AllArgsConstructor
@Slf4j
@Api("雨水收集")
public class RainwaterCollectionWebController extends BasicController {

    private final IRainwaterCollectionService iRainwaterCollectionService;

    @GetMapping("/overview")
    @ApiOperation("雨水收集总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>RainwaterCollectionWebController.overview入参为:空");
        List<RainwaterCollectionVO> rainwaterCollectionVOList = iRainwaterCollectionService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<RainwaterCollectionWebController.overview出参为:{}",rainwaterCollectionVOList.size());
        return returnSuccess(rainwaterCollectionVOList);
    }

    @PutMapping("/operation")
    @ApiOperation("修改雨水收集的值")
    public Response operation(@RequestBody RainwaterCollectionDTO rainwaterCollectionDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>RainwaterCollectionWebController.operation入参为:{}", rainwaterCollectionDTO);
        if(StringUtils.isBlank(rainwaterCollectionDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iRainwaterCollectionService.update(rainwaterCollectionDTO);
        return returnSuccess();
    }
}
