package com.landleaf.ibsaas.web.web.cache.provider;

/**
 * @description 定时失效缓存数据提供者
 * @author wyl
 * @date 2019/3/22 0022 11:09
 * @version 1.0
*/
public interface ITTLCacheProvider<V> {

    /**
     * 加载单个元素
     * get
     *
     * @param key
     * @return V
     * @since:
     */
    V get(String key);

}
