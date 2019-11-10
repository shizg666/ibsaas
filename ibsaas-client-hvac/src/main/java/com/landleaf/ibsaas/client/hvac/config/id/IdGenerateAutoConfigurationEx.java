package com.landleaf.ibsaas.client.hvac.config.id;

import com.landleaf.ibsaas.common.utils.id.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: IdGenerateAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(IdGeneratePropertiesEx.class)
public class IdGenerateAutoConfigurationEx {

    @Autowired
    IdGeneratePropertiesEx idGenerateProperties;

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIdWorker init(){
        return new SnowflakeIdWorker(idGenerateProperties.getWorkId(),idGenerateProperties.getDatacenterId());
    }

}
