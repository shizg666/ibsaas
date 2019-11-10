package com.landleaf.ibsaas.web.web.config.request;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
* @Description: RequestContextListener自动装配
*/
@Configuration
@ConditionalOnProperty(prefix = "ibsaas.request.holder", name = "enable")
public class RequestHolderAutoConfiguration {

    /**
     * 增加RequestContextListener配置，可以全局获取request对象
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerServletListenerRegistrationBean() {
        return new ServletListenerRegistrationBean(new RequestContextListener());
    }
}
