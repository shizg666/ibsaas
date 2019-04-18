package com.landleaf.ibsaas.web.web.cache.redis;

import com.landleaf.ibsaas.web.web.cache.CacheManager;
import com.landleaf.ibsaas.web.web.cache.ICache;
import com.landleaf.ibsaas.web.web.cache.exception.KeyIsNotFoundException;
import com.landleaf.ibsaas.web.web.cache.exception.ValueIsBlankException;
import com.landleaf.ibsaas.web.web.cache.exception.ValueIsNullException;
import com.landleaf.ibsaas.web.web.cache.provider.ITTLCacheProvider;
import com.landleaf.ibsaas.web.web.cache.redis.storage.RedisCacheStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
* @ClassName: DefaultTTLRedisCache
* @Description: TTL类型的缓存
* @author 高佳
* @date 2015年4月22日 下午1:31:57
*
* @param <V>
*/
public abstract class DefaultTTLRedisCache<V> implements ICache<String, V>, InitializingBean, DisposableBean {


    /**
     * 日志类
     */
    private static final Log LOG = LogFactory.getLog(DefaultTTLRedisCache.class);
    
    /**
     * 数据提供者
     */
    protected ITTLCacheProvider<V> cacheProvider;
    
    /**
     * 数据存储器
     */
    protected RedisCacheStorage<String, V> cacheStorage;
    
    /**
     * 超时时间,单位秒,默认10分钟
     */
    protected int timeOut = 10 * 60;
    
    /**
     * 设置数据提供者
     * @param cacheProvider
     * @see
     */
    public void setCacheProvider(ITTLCacheProvider<V> cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    /**
     * 设置数据存储者
     * @param cacheStorage
     * @see
     */
    public void setCacheStorage(RedisCacheStorage<String, V> cacheStorage) {
        this.cacheStorage = cacheStorage;
    }
    
    /**
     * 设置超时时间
     * @param seconds
     * @see
     */
    public void setTimeOut(int seconds) {
        this.timeOut = seconds;
    }
    
    /**
     * 根据uuid和key生成key
     * @param key
     * @return
     * @see
     */
    protected String getKey(String key) {
        return getUUID() + "_" + key;
    }


    public boolean set(String key,V value,int timeOut){
        return cacheStorage.set(getKey(key), value, timeOut);
    }
    public boolean setMaxInactiveInterval(String key,int timeOut){
        return cacheStorage.set(getKey(key), get(key), timeOut);
    }

    public boolean set(String key,V value){
        return cacheStorage.set(getKey(key), value, timeOut);
    }

    /** 
     * 获取数据
     * 如果返回null就是真的没有数据，无需再去数据库再取
     * 
     * @param key
     * @return 
     */
    public V get(String key) {
        if(StringUtils.isEmpty(key)) {
            LOG.warn("缓存["+getUUID()+"]，key为空串，返回结果[null]");
            //key存在，value为空串
            return null;
        }
        V value = null;
        try {
            value = cacheStorage.get(getKey(key));
        } catch(ValueIsBlankException e) {
            LOG.warn("缓存["+getUUID()+"]，key["+key+"]存在，value为空串，返回结果[null]");
            //key存在，value为空串
            return null;
        } catch(ValueIsNullException ex) {
            //key存在，value为null
            LOG.warn("缓存["+getUUID()+"]，key["+key+"]存在，value为null，返回结果[null]");
            return null;
        } catch(KeyIsNotFoundException ex1) {
            //key不存在
            value = cacheProvider.get(key);
            LOG.warn("缓存["+getUUID()+"]，key["+key+"]不存在，走数据库查询，返回结果["+value+"]");
            cacheStorage.set(getKey(key), value, timeOut);
        } catch(RedisConnectionFailureException exx) {
            //redis 连接出现异常
            value = cacheProvider.get(key);
            LOG.warn("redis连接出现异常，走数据库查询!");
            LOG.error(exx);
            return value;
        } catch (Exception e){
            //其他异常
            LOG.error(e);
            value = cacheProvider.get(key);
            return value;
        }
        return value;
    }

    public Map<String, V> get() {
        throw new RuntimeException(getUUID() + ":TTLCache cannot get all!");
    }

    public void invalid() {
        throw new RuntimeException(getUUID() + ":TTLCache cannot invalid all!");
    }

    /** 
     * 失效数据
     * @param key 
     */
    public void invalid(String key) {
        cacheStorage.remove(getKey(key));
    }
    
    public void invalidMulti(String ... keys) {
        if(keys == null) return;
        String[] skeys = new String[keys.length];
        for(int i=0;i<keys.length;i++) {
            skeys[i] = getKey(keys[i]);
        }
        cacheStorage.removeMulti(skeys);
    }

    public void destroy() throws Exception {
        CacheManager.getInstance().unRegisterCache(getUUID());
    }

    public void afterPropertiesSet() throws Exception {
        CacheManager.getInstance().registerCache(this);
    }

}
