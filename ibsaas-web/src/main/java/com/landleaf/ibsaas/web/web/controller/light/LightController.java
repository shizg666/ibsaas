package com.landleaf.ibsaas.web.web.controller.light;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.constant.RedisConstants;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightStateRequestVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightAreaResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.common.enums.light.LightProcotolEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.ITLightAreaDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private RedisHandle redisHandle;
    @Autowired
    private ITLightAreaDeviceService itLightAreaDeviceService;
    @Value("${light.control}")
    private Boolean control;

    @PostMapping("/controlLight")
    @ApiOperation(value = "控制灯光", notes = "控制灯光")
    public Response controlLight(@RequestBody LightMsg requestBody) {
        if (!control){
            throw new BusinessException("您没有该权限！");
        }
        iLightService.controlLight(requestBody);
        return returnSuccess();
    }

//    @GetMapping("/getPositionListByFloor/{id}")
//    @ApiOperation(value = "根据楼层获取灯光管理信息（包含属性）", notes = "根据楼层获取灯光位置信息")
//    public Response<TLightPositionResponseVO> getPositionListByFloor(@PathVariable("id") Long id) {
//        List<TLightPositionResponseVO> data = itLightPositionService.getPositionAtrributeListByFloor(id);
//        return returnSuccess(data);
//    }

    @GetMapping("/getAreaListByFloor/{id}")
    @ApiOperation(value = "根据楼层获取灯光管理信息（包含属性）", notes = "根据楼层获取灯光位置信息")
    public Response<TLightPositionResponseVO> getAreaListByFloor(@PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        List<TLightAreaResponseVO> data = itLightAreaDeviceService.getPositionAtrributeListByFloor(id);
        long t = System.currentTimeMillis() - start;
        System.out.println("###########################################################################################"+t);
        return returnSuccess(data);
    }




    @GetMapping("/getStateByAdress")
    @ApiOperation(value = "根据地址获取设备当前状态信息", notes = "")
    public Response<String> getStateByAdress(@Valid  @ApiParam LightStateRequestVO requestVO) {
//        TFloor tFloor = floorCommonService.getFloorById(requestVO.getFloorId());
        int floor = requestVO.getFloorId();
        String key = "";
        if (floor == 3){
            key =  RedisConstants.LIGHT_DEVICE_3F;
        }else if (floor == 4){
            key =  RedisConstants.LIGHT_DEVICE_4F;
        }
        String state = redisHandle.getMapField(key,requestVO.getAdress());
        if (StringUtil.isBlank(state)){
            //如果是空手动拉取一下
            LightMsg lightMsg = new LightMsg();
            lightMsg.setAdress(requestVO.getAdress());
            lightMsg.setFloor(String.valueOf(floor));
            lightMsg.setType("3");
            iLightService.controlLight(lightMsg);
            //
            state = iLightService.getTryLightState(key,requestVO.getAdress(),2000L);
        }
        return returnSuccess(state,null);
    }
    @GetMapping("/getProtocolList")
    @ApiOperation(value = "获取灯光协议列表", notes = "获取灯光协议列表")
    public Response<List<ChoiceButton>> getProtocolList() {
//        Map<Long, String> data = tLightProducts.stream().collect(Collectors.toMap(TLightType::getId, TLightType::getName));
        List<ChoiceButton> protocols = Lists.newArrayList();
        for (LightProcotolEnum procotolEnum : LightProcotolEnum.values()) {
            ChoiceButton choiceButton = new ChoiceButton();
            choiceButton.setChoiceKey(procotolEnum.getType());
            choiceButton.setChoiceValue(procotolEnum.getName());
            protocols.add(choiceButton);
        }
        return returnSuccess(protocols);
    }

}
