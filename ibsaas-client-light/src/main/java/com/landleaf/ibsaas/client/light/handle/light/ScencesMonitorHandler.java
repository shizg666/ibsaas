package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.client.light.constant.LightHandleConstant;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.springframework.stereotype.Component;

/**
 *R1AUTO1!
 * 1= Send the command when the scene changes
 * 0= Switch off the function 关闭该功能
 */
@HandlerType(type= LightHandleConstant.HANDLE_SECNCES_MONITOR,desc = "场景变换监听处理器(R1G2AUTO1!)")
@Component
public class ScencesMonitorHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        StringBuilder command = new StringBuilder();
//        String region = lightMsg.getRegion();
        String adress = lightMsg.getAdress();
        String vaule = lightMsg.getValue();
//        if (StringUtil.isNotEmpty(region)){
//            command.append("R").append(region);
//        }
        command.append(adress);
        if (StringUtil.isNotEmpty(vaule)){
            command.append("AUTO").append(vaule).append("!");
        }
        return command.toString();
    }
}
