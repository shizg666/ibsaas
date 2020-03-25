package com.landleaf.ibsaas.web.asyn;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface IFutureService {

    @Async
    public java.util.concurrent.Future getCacheFuture(String msgId, Long timeout);
    @Async
    public Future getKnightCacheFuture(String msgId, Long timeout);
    @Async
    public Future getParkingCacheFuture(String msgId, Long timeout);
}
