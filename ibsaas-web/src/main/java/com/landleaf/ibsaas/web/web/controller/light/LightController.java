package com.landleaf.ibsaas.web.web.controller.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.BasePageVO;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光操作")
public class LightController extends BasicController {

    @Autowired
    private ILightService iLightService;

    @PostMapping("/controlLight")
    @ApiOperation(value = "控制某个灯光", notes = "灯光设备列表分页查询")
    public Response controlLight(@RequestBody LightMsg requestBody) {
        iLightService.controlLight(requestBody);
        return returnSuccess();
    }


}
