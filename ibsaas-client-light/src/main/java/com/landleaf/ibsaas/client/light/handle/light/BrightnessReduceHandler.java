package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.springframework.stereotype.Component;


@HandlerType(type="3",desc = "灯光亮度-减小-处理器")
@Component
public class BrightnessReduceHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        //02 16进制是ASCII控制字符
        StringBuilder command = new StringBuilder("\u0002");
        String region = lightMsg.getRegion();
        String value = lightMsg.getValue();
        if (StringUtil.isNotEmpty(region)){
            command.append("R").append(region);
        }
        if (StringUtil.isNotEmpty(value)){
            command.append("T2").append("D-").append(value);
        }
        //03 16进制是ASCII控制字符
        command.append("\u0003");
        return command.toString();
    }
}
