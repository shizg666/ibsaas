package com.landleaf.ibsaas.web.tcp.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapCacheUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentHashMapCacheUtils.class);

    /**
     * 缓存最大个数
     */
    private static final Integer CACHE_MAX_NUMBER = 100000;
    /**
     * 当前缓存个数
     */
    private static Integer CURRENT_SIZE = 0;
    /**
     * 时间一分钟
     */
    static Long ONE_MINUTE = 1 * 60 * 1000L;
    /**
     * 缓存对象
     */
    private static final Map<String, CacheObj> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();
    /**
     * 这个记录了缓存使用的最后一次的记录，最近使用的在最前面
     */
    private static final List<String> CACHE_USE_LOG_LIST = new LinkedList<>();
    /**
     * 清理过期缓存是否在运行
     */
    private static Boolean CLEAN_THREAD_IS_RUN = false;

    /**
     * 清理线程的
     */
    private static ExecutorService executor = Executors.newSingleThreadExecutor();


    /**
     * 设置缓存
     */
    public static void setCache(String cacheKey, Object cacheValue, long cacheTime) {
        Long ttlTime = null;
        if (cacheTime <= 0L) {
            if (cacheTime == -1L) {
                ttlTime = -1L;
            } else {
                return;
            }
        }
        checkSize();
        saveCacheUseLog(cacheKey);
        CURRENT_SIZE = CURRENT_SIZE + 1;
        if (ttlTime == null) {
            ttlTime = System.currentTimeMillis() + cacheTime;
        }
        CacheObj cacheObj = new CacheObj(cacheValue, ttlTime);
        CACHE_OBJECT_MAP.put(cacheKey, cacheObj);
        LOGGER.info("have set key :" + cacheKey);
    }

    /**
     * 设置缓存
     */
    public static void setCache(String cacheKey, Object cacheValue) {
        setCache(cacheKey, cacheValue, -1L);
    }

    /**
     * 获取缓存
     */
    public static Object getCache(String cacheKey, Long timeout) {
        startCleanThread();

        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        while (System.currentTimeMillis() < expireTimeMillis) {
            if (checkCache(cacheKey)) {
                Object cacheValue = CACHE_OBJECT_MAP.get(cacheKey).getCacheValue();
                if (cacheValue != null) {
                    return cacheValue;
                }
            }
        }
        return null;
    }

    public static boolean isExist(String cacheKey) {
        return checkCache(cacheKey);
    }

    /**
     * 删除所有缓存
     */
    public static void clear() {
        LOGGER.info("have clean all key !");
        CACHE_OBJECT_MAP.clear();
        CURRENT_SIZE = 0;
    }

    /**
     * 删除某个缓存
     */
    public static void deleteCache(String cacheKey) {
        Object cacheValue = CACHE_OBJECT_MAP.remove(cacheKey);
        if (cacheValue != null) {
            LOGGER.info("have delete key :" + cacheKey);
            CURRENT_SIZE = CURRENT_SIZE - 1;
        }
    }

    /**
     * 判断缓存在不在,过没过期
     */
    private static boolean checkCache(String cacheKey) {
        CacheObj cacheObj = CACHE_OBJECT_MAP.get(cacheKey);
        if (cacheObj == null) {
            return false;
        }
        if (cacheObj.getTtlTime() == -1L) {
            return true;
        }
        if (cacheObj.getTtlTime() < System.currentTimeMillis()) {
            deleteCache(cacheKey);
            return false;
        }
        return true;
    }

    /**
     * 删除最近最久未使用的缓存
     */
    private static void deleteLRU() {
        LOGGER.info("delete Least recently used run!");
        String cacheKey = CACHE_USE_LOG_LIST.remove(CACHE_USE_LOG_LIST.size() - 1);
        deleteCache(cacheKey);
    }

    /**
     * 删除过期的缓存
     */
    static void deleteTimeOut() {
        LOGGER.info("delete time out run!");
        List<String> deleteKeyList = new LinkedList<>();
        for (Map.Entry<String, CacheObj> entry : CACHE_OBJECT_MAP.entrySet()) {
            if (entry.getValue().getTtlTime() < System.currentTimeMillis() && entry.getValue().getTtlTime() != -1L) {
                deleteKeyList.add(entry.getKey());
            }
        }

        for (String deleteKey : deleteKeyList) {
            deleteCache(deleteKey);
        }
        LOGGER.info("delete cache count is :" + deleteKeyList.size());

    }

    /**
     * 检查大小
     * 当当前大小如果已经达到最大大小
     * 首先删除过期缓存，如果过期缓存删除过后还是达到最大缓存数目
     * 删除最久未使用缓存
     */
    private static void checkSize() {
        if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
            deleteTimeOut();
        }
        if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
            deleteLRU();
        }
    }

    /**
     * 保存缓存的使用记录
     */
    private static synchronized void saveCacheUseLog(String cacheKey) {
        CACHE_USE_LOG_LIST.remove(cacheKey);
        CACHE_USE_LOG_LIST.add(0, cacheKey);
    }

    /**
     * 设置清理线程的运行状态为正在运行
     */
    static void setCleanThreadRun() {
        CLEAN_THREAD_IS_RUN = true;
    }

    /**
     * 开启清理过期缓存的线程
     */
    private static void startCleanThread() {
        if (!CLEAN_THREAD_IS_RUN) {
//            new Thread(new CleanTimeOutThread()).run();
            executor.submit(new CleanTimeOutThread());
        }
    }

    public static void showUtilsInfo() {
        System.out.println("clean time out cache is run :" + CLEAN_THREAD_IS_RUN);
        System.out.println("cache max count is :" + CACHE_MAX_NUMBER);
        System.out.println("cache current count is :" + CURRENT_SIZE);
        System.out.println("cache object map is :" + CACHE_OBJECT_MAP.toString());
        System.out.println("cache use log list is :" + CACHE_USE_LOG_LIST.toString());

    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(2 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConcurrentHashMapCacheUtils.setCache("my_cache_key_" + i, i, 60 * 1000);
        }

        for (int i = 0; i < 100; i++) {
            if (i > 10) {
                ConcurrentHashMapCacheUtils.getCache("test",30*1000L);
            }
            try {
                Thread.sleep(2 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConcurrentHashMapCacheUtils.showUtilsInfo();
        }

    }


}

class CacheObj {
    /**
     * 缓存对象
     */
    private Object CacheValue;
    /**
     * 缓存过期时间
     */
    private Long ttlTime;

    CacheObj(Object cacheValue, Long ttlTime) {
        CacheValue = cacheValue;
        this.ttlTime = ttlTime;
    }

    Object getCacheValue() {
        return CacheValue;
    }

    Long getTtlTime() {
        return ttlTime;
    }

    @Override
    public String toString() {
        return "CacheObj{" +
                "CacheValue=" + CacheValue +
                ", ttlTime=" + ttlTime +
                '}';
    }
}

/**
 * 每一分钟清理一次过期缓存
 */
class CleanTimeOutThread implements Runnable {

    @Override
    public void run() {
        ConcurrentHashMapCacheUtils.setCleanThreadRun();
        while (true) {
            System.out.println("clean thread run ");
            ConcurrentHashMapCacheUtils.deleteTimeOut();
            try {
                Thread.sleep(ConcurrentHashMapCacheUtils.ONE_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
