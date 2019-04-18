package com.landleaf.ibsaas.web.web.cache.exception;

/**
 * @description key存在，value为空
 * @author wyl
 * @date 2019/3/22  11:08
 * @version 1.0
*/
public class ValueIsBlankException extends RuntimeException {

    private static final long serialVersionUID = -7890860681143337363L;

    public ValueIsBlankException(String message) {
        super(message);
    }

    public ValueIsBlankException(Throwable e) {
        super(e);
    }

    public ValueIsBlankException(String message, Throwable cause) {
        super(message, cause);
    }

}
