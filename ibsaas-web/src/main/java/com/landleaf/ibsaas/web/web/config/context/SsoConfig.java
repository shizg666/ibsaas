package com.landleaf.ibsaas.web.web.config.context;

import com.landleaf.ibsaas.web.web.filter.sso.Conf;
import com.landleaf.ibsaas.web.web.filter.sso.SsoWebFilter;
import com.landleaf.ibsaas.web.web.context.user.SsoWebLoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SsoConfig {


    @Value("${ibsaas.sso.logout.path}")
    private String ssoLogoutPath;

    @Value("${ibsaas.sso.excluded.paths}")
    private String ssoExcludedPaths;


    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean ssoFilterRegistration(SsoWebLoginHelper ssoWebLoginHelper) {


        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("SsoWebFilter");
        registration.setOrder(3);
        registration.addUrlPatterns("/*");
        SsoWebFilter mySsoWebFilter = new SsoWebFilter();
        mySsoWebFilter.setApplicationContext(applicationContext);
        mySsoWebFilter.setSsoWebLoginHelper(ssoWebLoginHelper);
        registration.setFilter(mySsoWebFilter);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, ssoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, ssoExcludedPaths);
        return registration;
    }

}
