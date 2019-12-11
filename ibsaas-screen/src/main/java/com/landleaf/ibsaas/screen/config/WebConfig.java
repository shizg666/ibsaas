package com.landleaf.ibsaas.screen.config;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/11 15:09
 * @description:
 */
@EnableWebFlux
@Configuration
public class WebConfig {

    private static final String PREFIX_PATH = "/screen";

    @Autowired
    private ScreenWebSocketHandler screenWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
        final Map<String, WebSocketHandler> map = Maps.newHashMap();
        map.put("/sensors", screenWebSocketHandler);

        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
