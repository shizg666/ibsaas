package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.util.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 灯光处理器容器
 */
public class LightHandlerContext {

    private Map<String,Class> handlerMap;

    public LightHandlerContext(Map<String,Class> handlerMap){
        this.handlerMap = handlerMap;
    }

    public AbstractLightHandler getLightHandler(String type){
        Class clazz = handlerMap.get(type);
        if (clazz == null){
            throw new IllegalArgumentException("not found handler for type :"+type);
        }
        return (AbstractLightHandler) SpringUtil.getBean(clazz);
    }
}
