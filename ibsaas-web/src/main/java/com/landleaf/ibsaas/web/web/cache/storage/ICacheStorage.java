package com.landleaf.ibsaas.web.web.cache.storage;

import java.util.Map;


/**
 * @description 抽象的数据缓存仓库
 * @author wyl
 * @date 2019/3/22 0022 11:15
 * @version 1.0
*/
public interface ICacheStorage<K, V> {

    /**
     * 存放数据
     *
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * 批量设置数据
     *
     * @param values
     */
    void set(Map<K, V> values);

    /**
     * 获取数据
     *
     * @param key
     */
    V get(K key);

    /**
     * 移除指定的数据
     *
     * @param key
     */
    void remove(K key);

    /**
     * 批量获取数据
     *
     * @return
     */
    Map<K, V> get();

    /**
     * 是否存在
     *
     * @param key
     * @return
     * @see
     */
    Boolean exists(K key);

    /**
     * 移除所有的数据
     */
    void clear();
}
