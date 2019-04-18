package com.landleaf.ibsaas.web.web.cache.provider;

import java.util.Date;

/**
 * 基于时间刷新的缓存提供者
 */
public interface ITimeRefreshableCacheProvider<K, V> {
    /**
     * 获取最后修改时间
     */
    Date getLastModifyTime();
}
