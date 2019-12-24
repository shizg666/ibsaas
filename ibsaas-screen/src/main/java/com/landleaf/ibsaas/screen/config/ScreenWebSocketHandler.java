package com.landleaf.ibsaas.screen.config;

import com.landleaf.ibsaas.screen.service.LargeScreenService;
import com.landleaf.ibsaas.screen.service.ScreenAsyncService;
import com.landleaf.ibsaas.screen.service.ScreenFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/12/11 14:18
 * @description:
 */
@Component
public class ScreenWebSocketHandler implements WebSocketHandler {

    @Autowired
    private LargeScreenService largeScreenService;

    @Autowired
    private ScreenAsyncService screenAsyncService;

    @Autowired
    private ScreenFluxService fluxService;


    private static Map<String, WebSocketSession> map = new ConcurrentHashMap<>(64);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        map.put(session.getId(), session);

        return session.send(Flux.just(session.textMessage(session.getId() + "-" + System.currentTimeMillis())));
    }



    private void log(){
        map.forEach( (k,v) -> System.out.println(k + "------->" + v));

    }



}
