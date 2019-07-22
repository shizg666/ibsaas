package com.landleaf.ibsaas.web.web.controller.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.BasePageVO;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ITLightDeviceService;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光设备操作")
@Slf4j
public class LightDeviceController extends BasicController {
    @Autowired
    private ITLightDeviceService itLightDeviceService;

    @GetMapping("/device/{id}")
    @ApiOperation(value = "根据主键id获取设备信息", notes = "根据主键id获取设备信息")
    public Response getDeviceById(@PathVariable Long id) {
        LightDeviceResponseVO data = itLightDeviceService.getDeviceById(id);
        return returnSuccess(data);
    }


    @GetMapping("/device/getDeviceRecord")
    @ApiOperation(value = "灯光设备列表分页查询", notes = "灯光设备列表分页查询")
    public Response getDeviceRecord(TLightDeviceQueryVO requestBody) {
        PageInfo<LightDeviceResponseVO> data = itLightDeviceService.getDeviceRecordByCondition(requestBody);
        BasePageVO<LightDeviceResponseVO> result = new BasePageVO<>(data.getList(), data.getTotal());
        return returnSuccess(result);
    }


    @ApiOperation(value = "添加或者修改灯光设备信息", notes = "添加或者修改灯光设备信息")
    @PostMapping(value = "/device/addOrUpdateProduct")
    public Response<TLightDevice> addOrUpdateDevice(@RequestBody @ApiParam TLightDeviceRequestVO tLightDeviceRequestVO) {

        TLightDevice result = itLightDeviceService.addOrUpdateDevice(tLightDeviceRequestVO);
        String message;
        if (tLightDeviceRequestVO.getId() == null || tLightDeviceRequestVO.getId() == 0L) {
            message = "添加成功！";
        } else {
            message = "修改成功！";
        }
        return returnSuccess(result, message);
    }

    @PostMapping("/device/delete/{id}")
    @ApiOperation(value = "根据id删除灯光产品", notes = "")
    public Response deleteDevice(@PathVariable @ApiParam(name = "id", value = "设备id", required = true) Long id) {
        log.info("LightDeviceController >>>>>>>>>>>>>>>>>> deleteDevice id:{}", id);
        Integer result = itLightDeviceService.deleteDevice(id);
        return returnSuccess(result, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }
}
