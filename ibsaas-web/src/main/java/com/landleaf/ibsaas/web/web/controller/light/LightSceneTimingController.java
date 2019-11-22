package com.landleaf.ibsaas.web.web.controller.light;


import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingReqVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingRespVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightTimingSwitchReqVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ILightSceneTimingService;
import com.landleaf.ibsaas.web.web.service.light.impl.LightSceneTimingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  区域定时前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
@RestController
@RequestMapping("/light/timing")
@Api(value = "/light/timing", description = "灯光定时操作")
public class LightSceneTimingController extends BasicController {

    @Autowired
    private ILightSceneTimingService iLightSceneTimingService;

    @ApiOperation(value = "添加区域定时", notes = "")
    @PostMapping(value = "/add")
    public Response addAreaTime(@RequestBody LightSceneTimingReqVO reqVO){
        iLightSceneTimingService.addAreaTime(reqVO);
        return returnSuccess();
    }

    @ApiOperation(value = "定时开关", notes = "添加或者修改灯光产品信息")
    @PostMapping(value = "/switch")
    public Response timeSwitch(@RequestBody LightTimingSwitchReqVO reqVO){
        iLightSceneTimingService.timeSwitch(reqVO);
        return returnSuccess();
    }

    @GetMapping(value = "/get-list/{areaId}")
    public Response<List<LightSceneTimingRespVO>> getListAreaTime(@PathVariable("areaId") Long areaId){
        List<LightSceneTimingRespVO> data = iLightSceneTimingService.getListAreaTime(areaId);
        return returnSuccess(data);
    }

}
