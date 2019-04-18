package com.landleaf.ibsaas.web.web.cache.exception;

/**
 * @description key不存在异常
 * @author wyl
 * @date 2019/3/22 0022 11:08
 * @version 1.0
*/
public class KeyIsNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4928429573985189018L;

    public KeyIsNotFoundException(String message) {
        super(message);
    }

    public KeyIsNotFoundException(Throwable e) {
        super(e);
    }

    public KeyIsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
