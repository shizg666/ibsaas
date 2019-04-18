package com.landleaf.ibsaas.web.web.config.cache;

import com.landleaf.ibsaas.web.web.cache.CacheManager;
import com.landleaf.ibsaas.web.web.cache.redis.storage.RedisCacheStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description Redis缓存存储自动装配
 * @author wyl
 * @date 2019/3/22 0022 11:20
 * @version 1.0
*/
@Configuration
@ConditionalOnClass(CacheManager.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.redis", name = "enable")
public class RedisStorageAutoConfiguration {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "redisCacheStorage")
    public RedisCacheStorage redisCacheStorage(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        RedisCacheStorage storage = new RedisCacheStorage();
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        storage.setRedisTemplate(redisTemplate);
        return storage;
    }

}
