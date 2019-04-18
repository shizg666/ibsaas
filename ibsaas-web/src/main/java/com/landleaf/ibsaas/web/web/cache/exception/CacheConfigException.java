package com.landleaf.ibsaas.web.web.cache.exception;

/**
 * @description 缓存配置异常
 * @author wyl
 * @date 2019/3/22 0022 11:08
 * @version 1.0
*/
public class CacheConfigException extends RuntimeException {

    private static final long serialVersionUID = 4717145799805874827L;

    public CacheConfigException(String msg) {
        super(msg);
    }

}
