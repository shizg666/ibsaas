package com.landleaf.ibsaas.web.web.controller.light;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightReponseStateVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光管理操作")
public class LightController extends BasicController {

    @Autowired
    private ILightService iLightService;
    @Autowired
    private ITLightPositionService itLightPositionService;

    @PostMapping("/controlLight")
    @ApiOperation(value = "控制灯光", notes = "控制灯光")
    public Response controlLight(@RequestBody LightMsg requestBody) {
        iLightService.controlLight(requestBody);
        return returnSuccess();
    }


    @GetMapping("/getPositionListByFloor/{id}")
    @ApiOperation(value = "根据楼层获取灯光管理信息（包含属性）", notes = "根据楼层获取灯光位置信息")
    public Response<TLightPositionResponseVO> getPositionListByFloor(@PathVariable("id") Long id) {
        List<TLightPositionResponseVO> data = itLightPositionService.getPositionAtrributeListByFloor(id);
        return returnSuccess(data);
    }

    @GetMapping("/getPositionListByFloor/{id}")
    @ApiOperation(value = "根据楼层获取灯光管理信息（包含属性）", notes = "根据楼层获取灯光位置信息")
    public Response<LightReponseStateVO> getState(@PathVariable("id") Long id) {
        List<LightReponseStateVO> data = itLightPositionService.getPositionAtrributeListByFloor(id);
        return returnSuccess(data);
    }


}
