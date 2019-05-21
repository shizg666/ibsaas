package com.landleaf.ibsaas.client.knight.restful.converter;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: FastJson自动装载
*/
@Configuration
@EnableConfigurationProperties(FastJsonMessageConverterProperties.class)
@ConditionalOnProperty(prefix = "ibsaas.fastjson", name = "enable")
public class FastJsonMessageConverterAutoConfiguration {

    @Autowired
    private FastJsonMessageConverterProperties fastJsonMessageConverterProperties;

    /**
     * 配置fastjson转换类
     */
    @Bean
    public FastJsonHttpMessageConverter4 fastJsonHttpMethodMessageConverter() {
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter4();
        if (!CollectionUtils.isEmpty(supportedMediaTypes())) {
            fastJsonHttpMessageConverter4.setSupportedMediaTypes(supportedMediaTypes());
        }
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig());
        return fastJsonHttpMessageConverter4;
    }

    /**
     * 配置可处理的消息类型
     */
    private List<MediaType> supportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        List<String> supportedMediaTypesConfig = fastJsonMessageConverterProperties.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(supportedMediaTypesConfig)) {
            for (String supportedMediaType : supportedMediaTypesConfig) {
                supportedMediaTypes.add(MediaType.valueOf(supportedMediaType));
            }
        }
        return supportedMediaTypes;
    }

    /**
     * 配置转换规则
     */
    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig config = new FastJsonConfig();
        List<String> featuresConfig = fastJsonMessageConverterProperties.getFeatures();
        if (!CollectionUtils.isEmpty(featuresConfig)) {
            Feature[] features = new Feature[featuresConfig.size()];
            for (int i = 0; i < featuresConfig.size(); i++) {
                features[i] = Feature.valueOf(featuresConfig.get(i));
            }
            config.setFeatures(features);
        }
        return config;
    }

}
