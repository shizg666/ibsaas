package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import org.springframework.stereotype.Component;

@HandlerType(type="1",desc = "灯光关闭控制处理器")
@Component
public class SwitchHandler extends AbstractLightHandler {
    @Override
    public String getCommand(LightMsg lightMsg) {
        return "";
    }
}
