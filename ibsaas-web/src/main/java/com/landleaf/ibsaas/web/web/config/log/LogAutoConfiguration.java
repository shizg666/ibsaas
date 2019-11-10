package com.landleaf.ibsaas.web.web.config.log;

import com.landleaf.ibsaas.web.web.constant.UrlConstants;
import com.landleaf.ibsaas.web.web.filter.web.LogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;

/**
* @Description: 日志配置自动装配
*/
@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    @Autowired
    private LogProperties logProperties;

    /**
     * 增加日志拦截器配置
     */
    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnProperty(prefix = "ibsaas.log.filter", name = "enable")
    public FilterRegistrationBean logFilterRegistrationBean() {
        LogFilter logFilter = new LogFilter();
        logFilter.setNeedLogRequest(logProperties.getFilter().isNeedLogRequest());
        logFilter.setNeedLogResponse(logProperties.getFilter().isNeedLogResponse());
        logFilter.setNeedLogHeader(logProperties.getFilter().isNeedLogHeader());
        logFilter.setNeedLogPayload(logProperties.getFilter().isNeedLogPayload());
        if (!CollectionUtils.isEmpty(logProperties.getFilter().getExcludeUrlPatterns())) {
            logFilter.setExcludeUrlPatterns(logProperties.getFilter().getExcludeUrlPatterns());
        }
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(logFilter);
        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 60);
        return registrationBean;
    }

}
