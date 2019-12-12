package com.landleaf.ibsaas.screen.config;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.screen.service.LargeScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/11 14:18
 * @description:
 */
@Component
public class ScreenWebSocketHandler implements WebSocketHandler {

    @Autowired
    private LargeScreenService screenService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {


                Flux<Map<String, SensorVO>> flux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> screenService.sensorStatus());
        return session.send(flux.map(d -> session.textMessage(JSONUtil.toJsonStr(d))));
    }
}
