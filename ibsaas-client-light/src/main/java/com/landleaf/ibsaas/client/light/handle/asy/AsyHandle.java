package com.landleaf.ibsaas.client.light.handle.asy;

import com.landleaf.ibsaas.client.light.handle.light.LightHandlerContext;
import com.landleaf.ibsaas.client.light.handle.light.reponse.LightResponse;
import com.landleaf.ibsaas.client.light.util.SpringUtil;
import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class AsyHandle {
    @Autowired
    private LightHandlerContext lightHandlerContext;

    @Async("lightThreadPool")
    public void handle(LightMsgResponse lightMsgResponse){
        log.info("AsyHandle --------> 接收灯光消息：{}",lightMsgResponse.toString());
        try{
            Map<String,Class> handleMap = lightHandlerContext.getReponseHandlerMap();
            String patten = lightMsgResponse.getType();
            Class clazz = handleMap.get(patten);
            LightResponse lightResponse = (LightResponse) SpringUtil.getBean(clazz);
            lightResponse.handle(lightMsgResponse);
        }catch (Exception e){
            log.error("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 消息处理失败：{}",e.getMessage());
        }

    }
}
