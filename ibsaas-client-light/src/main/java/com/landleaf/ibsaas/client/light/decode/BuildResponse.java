package com.landleaf.ibsaas.client.light.decode;

import com.landleaf.ibsaas.client.light.handle.light.AbstractLightHandler;
import com.landleaf.ibsaas.client.light.handle.light.LightHandlerContext;
import com.landleaf.ibsaas.client.light.handle.light.reponse.LightResponse;
import com.landleaf.ibsaas.client.light.util.SpringUtil;
import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;
import com.landleaf.ibsaas.common.exception.BusinessException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BuildResponse {

    @Autowired
    private LightHandlerContext lightHandlerContext;

    LightMsgResponse getResponse(String str){
        Map<String,Class> handleMap = lightHandlerContext.getReponseHandlerMap();
        LightResponse lightResponse = null;
        String pattrn = "";
        for (Map.Entry<String, Class> entry : handleMap.entrySet()) {
            String k = entry.getKey();
            Class clazz = entry.getValue();
            Pattern r = Pattern.compile(k);
            Matcher m = r.matcher(str);
            if (m.matches()) {
                lightResponse = (LightResponse) SpringUtil.getBean(clazz);
                pattrn = k;
                break;
            }
        }
        if (lightResponse == null){
//            throw new BusinessException("reponseHandle not found! message:"+str);
            return null;
        }
        LightMsgResponse lightMsgResponse = lightResponse.getReponse(str);
        lightMsgResponse.setType(pattrn);
        return lightMsgResponse;
    }
}
