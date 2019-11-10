package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.dto.ExhaustBlowerDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.RainwaterCollectionDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.ExhaustBlowerVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.IExhaustBlowerService;
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
@RequestMapping("/exhaust-blower")
@AllArgsConstructor
@Slf4j
@Api("排风机")
public class ExhaustBlowerWebController extends BasicController {

    private final IExhaustBlowerService iExhaustBlowerService;

    @GetMapping("/overview")
    @ApiOperation("排风机总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ExhaustBlowerWebController.overview入参为:空");
        List<ExhaustBlowerVO> exhaustBlowerVOList = iExhaustBlowerService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<ExhaustBlowerWebController.overview出参为:{}",exhaustBlowerVOList.size());
        return returnSuccess(exhaustBlowerVOList);
    }

    @PutMapping("/operation")
    @ApiOperation("修改排风机的值")
    public Response operation(@RequestBody ExhaustBlowerDTO exhaustBlowerDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ExhaustBlowerWebController.operation入参为:{}", exhaustBlowerDTO);
        if(StringUtils.isBlank(exhaustBlowerDTO.getId())){
            throw new BusinessException("所传更改设备的节点id为空");
        }
        iExhaustBlowerService.update(exhaustBlowerDTO);
        return returnSuccess();
    }
}
