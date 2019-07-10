package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.springframework.stereotype.Component;


@HandlerType(type="2",desc = "灯光场景控制处理器")
@Component
public class ScencesHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        //02 16进制是ASCII控制字符
        StringBuilder command = new StringBuilder("\u0002");
        String region = lightMsg.getRegion();
        String scenes = lightMsg.getScenes();
        String group = lightMsg.getGroup();
        String device = lightMsg.getDevice();

        if (StringUtil.isNotEmpty(region)){
            command.append("R").append(region);
        }
        if (StringUtil.isNotEmpty(scenes)){
            command.append("S").append(scenes);
        }
        //03 16进制是ASCII控制字符
        command.append("\u0003");
//        if (StringUtil.isNotEmpty(group)){
//            command.append("G").append(group);
//        }
//        if (StringUtil.isNotEmpty(device)){
//            command.append("B").append(device);
//        }
        return command.toString();
    }
}
