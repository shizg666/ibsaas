package com.landleaf.ibsaas.web.web.cache.provider;

import java.util.Map;

/**
 * @description 批量加载缓存接口
 * @author wyl
 * @date 2019/3/22 0022 11:09
 * @version 1.0
*/
public interface IBatchCacheProvider<K, V> extends ITimeRefreshableCacheProvider<K, V> {
    
	/**
	 * 批量加载数据
	 * get
	 * @return
	 * @return Map<K,V>
	 */
    Map<K, V> get();
}
