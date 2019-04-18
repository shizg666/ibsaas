package com.landleaf.ibsaas.web.web.cache.exception;

/**
 * @description key存在，value为null
 * @author wyl
 * @date 2019/3/22 0022 11:08
 * @version 1.0
*/
public class ValueIsNullException extends RuntimeException {

    private static final long serialVersionUID = 5589666472517709851L;

    public ValueIsNullException(String message) {
        super(message);
    }

    public ValueIsNullException(Throwable e) {
        super(e);
    }

    public ValueIsNullException(String message, Throwable cause) {
        super(message, cause);
    }


}
