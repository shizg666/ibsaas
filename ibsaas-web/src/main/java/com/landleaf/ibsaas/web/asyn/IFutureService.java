package com.landleaf.ibsaas.web.asyn;

import org.springframework.scheduling.annotation.Async;

public interface IFutureService {

    @Async
    public java.util.concurrent.Future getCacheFuture(String msgId, Long timeout);
}
