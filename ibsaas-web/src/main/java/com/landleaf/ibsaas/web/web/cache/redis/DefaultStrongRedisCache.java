package com.landleaf.ibsaas.web.web.cache.redis;

import com.landleaf.ibsaas.web.web.cache.CacheManager;
import com.landleaf.ibsaas.web.web.cache.IRefreshableCache;
import com.landleaf.ibsaas.web.web.cache.exception.KeyIsNotFoundException;
import com.landleaf.ibsaas.web.web.cache.exception.ValueIsBlankException;
import com.landleaf.ibsaas.web.web.cache.exception.ValueIsNullException;
import com.landleaf.ibsaas.web.web.cache.provider.IBatchCacheProvider;
import com.landleaf.ibsaas.web.web.cache.redis.constant.RedisConstants;
import com.landleaf.ibsaas.web.web.cache.redis.exception.RedisConnectionException;
import com.landleaf.ibsaas.web.web.cache.redis.storage.RedisCacheStorage;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
 * @description 默认的只读缓存抽象实现，最终实现只需要提供数据提供者、存储、uuid等配置即可
 * @author wyl
 * @date 2019/3/22 0022 11:13
 * @version 1.0
*/
public abstract class DefaultStrongRedisCache<K, V> implements IRefreshableCache<K, V>, InitializingBean, DisposableBean {

    /**
     * 只读缓存数据提供者
     */
    private IBatchCacheProvider<K, V> cacheProvider;

    /**
     * 只读缓存的Redis存储
     */
    private RedisCacheStorage<K, V> cacheStorage;

    /**
     * 自动刷新间隔时间，单位秒，默认15分钟。
     */
    private long interval = 15L * 60 * 1000;

    /**
     * 缓存最后一次修改时间,作为刷新缓存的时间戳
     */
    private Date modifyTime;

    /**
     * 自动刷新缓存线程
     */
    private ReloadThread thread;

    /**
     * @description 创建缓存示例后，自动将实例注册到CacheManager中，并且进行只读缓存的初始化
     * @author wyl
     * @date 2019/3/22 0022 11:14
     * @version 1.0
    */
    public void afterPropertiesSet() throws Exception {
        //注册
        CacheManager.getInstance().registerCache(this);
        //初始化缓存
        cacheStorage.initializationStrongCache(getUUID(), cacheProvider.get());
        //设置缓存中数据的最后修改时间
        modifyTime = cacheProvider.getLastModifyTime();
        //启动刷新线程
        thread = new ReloadThread("STRONG_REDIS_CACHE_" + this.getUUID());
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 获取数据
     *
     * @param key 缓存Key
     */
    public V get(K key) {
        V value = null;
        try {
            value = cacheStorage.hget(getUUID(), key);
        } catch (ValueIsBlankException e) {
            //key存在，value为空串
            return null;
        } catch (ValueIsNullException ex) {
            //key存在，value为null
            return null;
        } catch (KeyIsNotFoundException ex1) {
            //key不存在
            return null;
        } catch (RedisConnectionException exx) {
            //redis 连接出现异常
            return getFromProvider(key);
        }
        return value;
    }

    /**
     * 从缓存数据提供者直接获取数据
     *
     * @param key
     * @return
     * @author 陈宇霖
     * @date 2017年08月07日09:36:56
     */
    private V getFromProvider(K key) {
        Map<K, V> all = cacheProvider.get();
        if (!CollectionUtils.isEmpty(all)) {
            return all.get(key);
        }
        return null;
    }

    /**
     * 获取此只读缓存中所有的数据
     *
     * @return
     * @author 陈宇霖
     * @date 2017年08月07日09:34:34
     */
    public Map<K, V> get() {
        try {
            return cacheStorage.hget(getUUID());
        } catch (RedisConnectionException e) {
            //redis 连接出现异常  
            return cacheProvider.get();
        }

    }

    /**
     * 从CacheManager中取消注册
     *
     * @throws Exception
     * @author 陈宇霖
     * @date 2017年08月07日09:40:51
     */
    public void destroy() throws Exception {
        CacheManager.getInstance().unRegisterCache(getUUID());
    }

    /**
     * 自动刷新缓存
     */
    private class ReloadThread extends Thread {
        private volatile int state;

        ReloadThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    state = 2;
                    //刷新间隔时间
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                try {
                    state = 1;
                    //如果最后更新时间早于当前时间, 更新所有数据
                    refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * <p>刷新策略</p>
     *
     * @return
     */
    public boolean refresh() {
        Date lastTime = cacheProvider.getLastModifyTime();
        if (modifyTime != null && lastTime != null && lastTime.after(modifyTime)) {
            Map<K, V> map = cacheProvider.get();
            for (Map.Entry<K, V> entry : map.entrySet()) {
                cacheStorage.hset(getUUID(), entry.getKey(), entry.getValue());
            }
            modifyTime = lastTime;
            return true;
        }
        return false;
    }

    /**
     * <p>重新初始化缓存数据</p>
     */
    public void invalid() {
        cacheStorage.hremove(getUUID());
        cacheStorage.hremove(RedisConstants.STRONG_CACHE_INIT_FLAG_PREFIX + getUUID());
        //因为是批量加载所以全部失效考虑重新加载的问题
        cacheStorage.initializationStrongCache(getUUID(), cacheProvider.get());
        modifyTime = cacheProvider.getLastModifyTime();
    }

    public void invalid(K key) {
        throw new RuntimeException("Strong Cache Cannot Invalid Part!");
        //cacheStorage.hremove(getUUID(),key);
    }

    public void invalidMulti(K... keys) {
        throw new RuntimeException("Strong Cache Cannot Invalid Part!");
    }

    public void setInterval(int seconds) {
        this.interval = (long) seconds * 1000;
    }

    public void setCacheProvider(IBatchCacheProvider<K, V> cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public void setCacheStorage(RedisCacheStorage<K, V> cacheStorage) {
        this.cacheStorage = cacheStorage;
    }
}
