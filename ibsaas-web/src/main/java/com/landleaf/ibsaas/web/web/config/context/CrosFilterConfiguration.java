package com.landleaf.ibsaas.web.web.config.context;

import com.landleaf.ibsaas.web.web.constant.UrlConstants;
import com.landleaf.ibsaas.web.web.filter.web.CorsFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CrosFilterConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "ibsaas.filter.cors", name = "enable")
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CorsFilter corsFilter = new CorsFilter();
        registrationBean.setFilter(corsFilter);
        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
