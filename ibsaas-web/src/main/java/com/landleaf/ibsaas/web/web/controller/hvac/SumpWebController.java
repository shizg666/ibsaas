package com.landleaf.ibsaas.web.web.controller.hvac;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.hvac.vo.SumpVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.hvac.ISumpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:24
 * @description:
 */
@RestController
@RequestMapping("/sump")
@AllArgsConstructor
@Slf4j
@Api("集水坑")
public class SumpWebController extends BasicController {

    private final ISumpService iSumpService;

    @GetMapping("/overview")
    @ApiOperation("集水坑总览")
    public Response overview(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>SumpWebController.overview入参为:空");
        List<SumpVO> sumpVOList = iSumpService.overview();
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<SumpWebController.overview出参为:{}",sumpVOList.size());
        return returnSuccess(sumpVOList);
    }

}
