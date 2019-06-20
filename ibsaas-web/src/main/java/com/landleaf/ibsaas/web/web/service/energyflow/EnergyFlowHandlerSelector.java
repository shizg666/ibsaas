package com.landleaf.ibsaas.web.web.service.energyflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 根据statusType转发到具体处理handler 策略
 */
@Component
public class EnergyFlowHandlerSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyFlowHandlerSelector.class);

    @Autowired
    private Map<String, EnergyFlowHandler> energyFlowHandlerMap;


    public EnergyFlowHandler selectHandler(String code) {
        EnergyFlowHandler handler = null;
        for (Entry<String, EnergyFlowHandler> entry : energyFlowHandlerMap.entrySet()) {
            //获取handler上注解的statusType
            EnergyFlowAnnoation annotation = entry.getValue().getClass().getAnnotation(EnergyFlowAnnoation.class);
            if (annotation == null) {
                continue;
            }
            String queryCode = annotation.code();
            if (!queryCode.equals(code)) {
                continue;
            }
            //获取该实例
            handler = entry.getValue();
            break;
        }
        return handler;
    }


}