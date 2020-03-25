package com.landleaf.ibsaas.web.web.cache.redis.constant;

import com.landleaf.ibsaas.common.utils.date.DateUtils;

import java.io.Serializable;

/**
 * @description Redis缓存使用的常量
 * @author wyl
 * @date 2019/3/22 0022 11:10
 * @version 1.0
*/
public class RedisConstants implements Serializable {



    /**
     * 日期信息缓存
     */
    public static final String DAY_INFO = "day_info_";

    /**
     * 只读缓存初始化标示的统一前缀
     */
    public static final String STRONG_CACHE_INIT_FLAG_PREFIX = "leo.redis.strong.initialization.";

    /**
     * 只读缓存正在初始化时加锁标志的统一前缀
     */
    public static final String STRONG_CACHE_INIT_LOCKING_FLAG_PREFIX = "leo.redis.strong.lock.";

    /**
     * 只读缓存正在初始化时加锁的缓存对应的值，无实际意义，只要有此缓存就认为正在初始化
     */
    public static final String STRONG_CACHE_INIT_LOCKING_CACHE_VALUE = "1";

    /**
     * 只读缓存重试初始化的间隔时间，同时只能有一个只读缓存在初始化，后续的缓存每个指定的间隔时间再进行初始化
     */
    public static final int STRONG_RETRY_INIT_INTERVAL = 60 * 1000;

    /**
     * 未指定失效时间的TTL缓存，给定一个默认失效时间10分钟
     */
    public static final int TTL_DEFAULT_EXPIRED_TIME = 10 * 60;
    /**
     * 水表-分项-key
     */
    public static final String ENERGY_WATER_DATA_TYPE = "energy_water_data_type";
    /**
     * 水表-分区-key
     */
    public static final String ENERGY_WATER_DATA_AREA = "energy_water_data_area";
    /**
     * 电表-分项-key
     */
    public static final String ENERGY_ELECTRICITY_DATA_TYPE = "energy_electricity_type";
    /**
     * 电表-分区-key
     */
    public static final String ENERGY_ELECTRICITY_DATA_AREA = "energy_electricity_area";

}
