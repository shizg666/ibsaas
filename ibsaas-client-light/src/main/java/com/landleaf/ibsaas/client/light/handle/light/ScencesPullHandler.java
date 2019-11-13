package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.client.light.constant.LightHandleConstant;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import org.springframework.stereotype.Component;

/**
 *?  /轮询场景，调整值和错误/ C /第63页      ? / Poll the scene, adjustment value and errors / C / page 63
 */
@HandlerType(type = LightHandleConstant.HANDLE_SECNCES_PULL,desc = "手动轮询场景，调整值和错误(R1?)")
@Component
public class ScencesPullHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        StringBuilder command = new StringBuilder();
//        String region = lightMsg.getRegion();
        String adress = lightMsg.getAdress();
//        if (StringUtil.isNotEmpty(region)){
//            command.append("R").append(region);
//        }
        command.append(adress);
        command.append("?");
        return command.toString();
    }
}
