package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.util.SpringUtil;

import java.util.Map;

/**
 * 灯光处理器容器
 */
public class LightHandlerContext {

    private Map<String,Class> handlerMap;
    private Map<String,Class> reponseHandlerMap;

    public LightHandlerContext(Map<String,Class> handlerMap,Map<String,Class>  reponseHandlerMap){
        this.handlerMap = handlerMap;
        this.reponseHandlerMap = reponseHandlerMap;
    }

    public AbstractLightHandler getLightHandler(String type){
        Class clazz = handlerMap.get(type);
        if (clazz == null){
            throw new IllegalArgumentException("not found handler for type :"+type);
        }
        return (AbstractLightHandler) SpringUtil.getBean(clazz);
    }

    public Map<String, Class> getReponseHandlerMap() {
        return reponseHandlerMap;
    }

    public void setReponseHandlerMap(Map<String, Class> reponseHandlerMap) {
        this.reponseHandlerMap = reponseHandlerMap;
    }
}
