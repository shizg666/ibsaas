package com.landleaf.ibsaas.client.parking.lifang.config.converter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
* @Description: FastJson转换参数配置
*/
@ConfigurationProperties(prefix = "ibsaas.fastjson")
public class FastJsonMessageConverterProperties {

    /**
     * 是否使用fastjson转换json
     */
    private boolean enable;

    /**
     * 支持的参数类型
     */
    private List<String> supportedMediaTypes;

    /**
     * 转换的配置
     */
    private List<String> features;

    public List<String> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setSupportedMediaTypes(List<String> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
}
