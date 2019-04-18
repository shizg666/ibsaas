package com.landleaf.ibsaas.web.web.cache.redis.exception;

/**
 * @description Redis缓存操作统一异常
 * @author wyl
 * @date 2019/3/22 0022 11:10
 * @version 1.0
*/
public class RedisCacheStorageException extends RuntimeException {

    private static final long serialVersionUID = -8149325382164622728L;

    public RedisCacheStorageException(String message) {
        super(message);
    }

    public RedisCacheStorageException(Throwable e) {
        super(e);
    }

    public RedisCacheStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
