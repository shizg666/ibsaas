package com.landleaf.ibsaas.client.parking.lifang.service.trackflow;

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
public class TrafficFlowHandlerSelector {

    private static final Logger logger = LoggerFactory.getLogger(TrafficFlowHandlerSelector.class);

    @Autowired
    private Map<String, TrafficFlowHandler> trafficFlowHandlerMap;


    public TrafficFlowHandler selectHandler(String code) {
        TrafficFlowHandler handler = null;
        for (Entry<String, TrafficFlowHandler> entry : trafficFlowHandlerMap.entrySet()) {
            //获取handler上注解的statusType
            TrafficFlowAnnoation annotation = entry.getValue().getClass().getAnnotation(TrafficFlowAnnoation.class);
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