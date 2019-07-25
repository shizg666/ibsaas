package com.landleaf.ibsaas.web.web.controller.light;

import com.landleaf.ibsaas.common.constant.RedisConstants;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightReponseStateVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightStateRequestVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.buliding.impl.FloorCommonService;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光管理操作")
public class LightController extends BasicController {

    @Autowired
    private ILightService iLightService;
    @Autowired
    private ITLightPositionService itLightPositionService;
    @Autowired
    private RedisHandle redisHandle;
    @Autowired
    private FloorCommonService floorCommonService;

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

    @GetMapping("/getStateByAdress")
    @ApiOperation(value = "根据地址获取设备当前状态信息", notes = "")
    public Response<String> getStateByAdress(@Valid  @ApiParam LightStateRequestVO requestVO) {
        TFloor tFloor = floorCommonService.getFloorById(requestVO.getFloorId());
        String key = "";
        if (tFloor.getFloor() == 3){
            key =  RedisConstants.LIGHT_DEVICE_3F;
        }else if (tFloor.getFloor() == 4){
            key =  RedisConstants.LIGHT_DEVICE_3F;
        }
        String state = redisHandle.getMapField(key,requestVO.getAdress());
        if (StringUtil.isBlank(state)){
            state = "0";
        }
        return returnSuccess(state);
    }


}
