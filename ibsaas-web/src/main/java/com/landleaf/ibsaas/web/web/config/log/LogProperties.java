package com.landleaf.ibsaas.web.web.config.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @Description: 日志配置
*/
@ConfigurationProperties(prefix = "ibsaas.log")
public class LogProperties {

    /**
     * 请求日志拦截器相关配置
     */
    private LogFilterProperties filter;

    public LogFilterProperties getFilter() {
        return filter;
    }

    public void setFilter(LogFilterProperties filter) {
        this.filter = filter;
    }
}
