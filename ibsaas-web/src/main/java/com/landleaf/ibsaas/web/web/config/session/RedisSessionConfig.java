package com.landleaf.ibsaas.web.web.config.session;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 基于redis session共享
 * @author wyl
 * @version 1.0
 * @description
 * @date 2019年03月22日 22:56
 */
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)//session过期时间(秒)
@Configuration
public class RedisSessionConfig
{
    @Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }
}

