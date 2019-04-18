package com.landleaf.ibsaas.web.web.config.context;

import com.landleaf.ibsaas.web.web.filter.sso.MyXxlSsoWebFilter;
import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxueli 2018-11-15
 */
@Configuration
public class XxlSsoConfig implements DisposableBean {


    @Value("${xxl.sso.server}")
    private String xxlSsoServer;

    @Value("${xxl.sso.logout.path}")
    private String xxlSsoLogoutPath;

    @Value("${xxl.sso.excluded.paths}")
    private String xxlSsoExcludedPaths;

    @Value("${xxl.sso.redis.address}")
    private String xxlSsoRedisAddress;
    @Value("${xxl.sso.redirecturl}")
    private String redirectUrl;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {

        // xxl-sso, redis init
        JedisUtil.init(xxlSsoRedisAddress);

        // xxl-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlSsoWebFilter");
        registration.setOrder(3);
        registration.addUrlPatterns("/*");
        MyXxlSsoWebFilter myXxlSsoWebFilter = new MyXxlSsoWebFilter();
        myXxlSsoWebFilter.setApplicationContext(applicationContext);
        registration.setFilter(myXxlSsoWebFilter);
        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);
        registration.addInitParameter(Conf.SSO_DEFAULT_REDIRECTURL, redirectUrl);
        return registration;
    }

    @Override
    public void destroy() throws Exception {

        // xxl-sso, redis close
        JedisUtil.close();
    }

}
