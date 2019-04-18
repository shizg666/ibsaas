package com.landleaf.ibsaas.web.web.cache.redis.exception;


/**
 * @description Redis连接异常
 * @author wyl
 * @date 2019/3/22 0022 11:11
 * @version 1.0
*/
public class RedisConnectionException extends RedisCacheStorageException {

    private static final long serialVersionUID = -4125135648861157964L;

    public RedisConnectionException(String message) {
        super(message);
    }

    public RedisConnectionException(Throwable e) {
        super(e);
    }

    public RedisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
