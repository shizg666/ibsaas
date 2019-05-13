package com.landleaf.ibsaas.web.asyn;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class FutureService implements IFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);

    @Async
    public Future getCacheFuture(String msgId, Long timeout) {
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        TCPMessage cache = null;
        try {
            while (System.currentTimeMillis() < expireTimeMillis) {
                Thread.sleep(100L);
                cache = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId);
                if (cache != null) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        Future<TCPMessage> future = (Future<TCPMessage>) new AsyncResult<TCPMessage>(cache);
        return future;
    }
}
