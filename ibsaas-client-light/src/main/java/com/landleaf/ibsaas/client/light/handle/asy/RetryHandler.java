//package com.landleaf.ibsaas.client.light.handle.asy;
//
//import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//
//import java.util.concurrent.Future;
//
//public class RetryHandler {
//
//    @Async
//    public Future retryRedis(String key, Long timeout) {
//        long currentTimeMillis = System.currentTimeMillis();
//        long expireTimeMillis = currentTimeMillis + timeout;
//        KnightMessage cache = null;
//        try {
//            while (System.currentTimeMillis() < expireTimeMillis) {
//                Thread.sleep(100L);
//                cache = (KnightMessage) ConcurrentHashMapCacheUtils.getCache(msgId);
//                if (cache != null) {
//                    break;
//                }
//            }
//        } catch (InterruptedException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        Future<KnightMessage> future = (Future<KnightMessage>) new AsyncResult<KnightMessage>(cache);
//        return future;
//    }
//}
