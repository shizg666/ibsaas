package com.landleaf.ibsaas.screen.config;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.screen.model.resp.ResponseResult;
import com.landleaf.ibsaas.screen.service.LargeScreenService;
import com.landleaf.ibsaas.screen.service.ScreenAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    @Override
    public Mono<Void> handle(WebSocketSession session) {

                Flux<ResponseResult> flux = Flux.interval(Duration.ofSeconds(1))
                        .map(l -> ResponseResult.success(screenAsyncService.asyncExecuteService()))
                        .onErrorReturn(ResponseResult.defaultError());
        return session.send(flux.map(d -> session.textMessage(JSONUtil.toJsonStr(d))));
    }
}
