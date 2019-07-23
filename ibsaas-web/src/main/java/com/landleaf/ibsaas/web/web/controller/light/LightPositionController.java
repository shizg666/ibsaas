package com.landleaf.ibsaas.web.web.controller.light;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.vo.*;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/light/device/position")
@Api(value = "/light/device/position", description = "灯光位置操作")
@Slf4j
public class LightPositionController extends BasicController {
    @Autowired
    private ITLightPositionService itLightPositionService;


    @GetMapping("/getUnPositionDeviceList")
    @ApiOperation(value = "获取未配置的设备列表", notes = "根据楼层获取灯光位置信息")
    public Response<LightPositionDeviceVO> getUnPositionDeviceList() {
        List<LightPositionDeviceVO> data = itLightPositionService.getUnPositionDeviceList();
        return returnSuccess(data);
    }

    @GetMapping("/getUnPositionDeviceList/{id}")
    @ApiOperation(value = "根据楼层Id获取位置列表", notes = "根据楼层获取灯光位置信息")
    public Response<LightPositionDeviceVO> getPositionDeviceList(@PathVariable("id") Long id) {
        List<LightPositionDeviceVO> data = itLightPositionService.getPositionDeviceList(id);
        return returnSuccess(data);
    }


    @ApiOperation(value = "添加或者修改灯光位置信息", notes = "添加或者修改灯光位置信息")
    @PostMapping(value = "/addOrUpdatePosition")
    public Response<TLightDevice> addOrUpdatePosition(@RequestBody @ApiParam TLightPositionRequestVO requestBody) {
        TLightPosition result = itLightPositionService.addOrUpdatePosition(requestBody);
        String message;
        if (requestBody.getId() == null || requestBody.getId() == 0L) {
            message = "添加成功！";
        } else {
            message = "修改成功！";
        }
        return returnSuccess(result, message);
    }

    @GetMapping("/device/delete/{id}")
    @ApiOperation(value = "根据id删除灯光位置", notes = "")
    public Response deletePosition(@PathVariable @ApiParam(name = "id", value = "位置id", required = true) Long id) {
        log.info("LightDeviceController >>>>>>>>>>>>>>>>>> deletePosition id:{}", id);
        Integer result = itLightPositionService.deletePositionById(id);
        return returnSuccess(result, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }
}
