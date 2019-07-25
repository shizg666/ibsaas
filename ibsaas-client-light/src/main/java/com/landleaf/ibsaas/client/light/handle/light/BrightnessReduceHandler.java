package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.springframework.stereotype.Component;


@HandlerType(type="8",desc = "灯光亮度-减小-处理器(R1T2D-8)")
@Component
public class BrightnessReduceHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        StringBuilder command = new StringBuilder();
        String region = lightMsg.getRegion();
        String value = lightMsg.getValue();
        if (StringUtil.isNotEmpty(region)){
            command.append("R").append(region);
        }
        if (StringUtil.isNotEmpty(value)){
            command.append("T2").append("D-").append(value);
        }
        return command.toString();
    }
}
