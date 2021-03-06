package com.landleaf.ibsaas.client.knight.asyn;

import com.landleaf.ibsaas.client.knight.domain.dto.KnightMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface IFutureService {

    @Async
    Future<KnightMessage> handlerMsg(KnightMessage kngihtMessage);
}
