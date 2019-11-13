package com.landleaf.ibsaas.client.knight.restful;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.landleaf.ibsaas.client.knight.restful.converter.FastJsonMessageConverterAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @version V1.0
 * @Title: RestTemplateConfiguration
 * @Description: restful接口调用客户端自动装配
 */
@Configuration
@EnableConfigurationProperties(RestfulTemplateProperties.class)
@AutoConfigureAfter({FastJsonMessageConverterAutoConfiguration.class})
public class RestTemplateConfiguration {

    @Autowired
    private RestfulTemplateProperties restfulTemplateProperties;

    @Autowired(required = false)
    private FastJsonHttpMessageConverter4 fastJsonHttpMethodMessageConverter;

    /**
     * 创建访问restful接口的客户端工具
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplateClient restTemplateClient() {
        RestTemplateClient restTemplateClient = new RestTemplateClient();
        restTemplateClient.setRestfulTemplateProperties(restfulTemplateProperties);
        restTemplateClient.setFastJsonHttpMessageConverter4(fastJsonHttpMethodMessageConverter);
        return restTemplateClient;
    }
}
