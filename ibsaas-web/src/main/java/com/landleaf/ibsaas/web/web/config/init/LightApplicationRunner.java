//package com.landleaf.ibsaas.web.web.config.init;
//
//import com.aikucun.delivery.util.CacheUtils;
//import com.aikucun.delivery.util.weixin.WeixinSupport;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * 启动完成后加载设备信息到Redis
// */
//@Component
//@Slf4j
//public class LightApplicationRunner implements ApplicationRunner {
//
//    @Autowired
//    private
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        WeixinSupport.loadConfig(weixinConfig);
//        log.info("======加载微信配置信息：{}======", WeixinSupport.isLoad());
//        CacheUtils.loadRedisTemplate(redisTemplate);
//        log.info("======初始redis缓存:{}======", CacheUtils.isLoad());
//    }
//}
