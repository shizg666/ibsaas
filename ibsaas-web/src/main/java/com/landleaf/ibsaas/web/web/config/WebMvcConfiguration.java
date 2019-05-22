package com.landleaf.ibsaas.web.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.landleaf.ibsaas.web.web.constant.UrlConstants;
import com.landleaf.ibsaas.web.web.context.SpringApplicationContextHolder;
import com.landleaf.ibsaas.web.web.filter.web.IpWhiteListFilter;
import com.landleaf.ibsaas.web.web.filter.web.ParamsVerifyFilter;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

/**
* @Description: mvc相关配置
*/
@Configuration
@ConditionalOnWebApplication
public class WebMvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Autowired
    private MessageSource messageSource;

    private ApplicationContext applicationContext;


    /**
     * 增加使用fastjson消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter4 converter = null;
        try {
            converter = applicationContext.getBean("fastJsonHttpMethodMessageConverter", FastJsonHttpMessageConverter4.class);
        } catch (NoSuchBeanDefinitionException e) {
            logger.warn("fastJsonHttpMethodMessageConverter bean not created, will use default messageConverter");
        }
        if (converter != null) {
            converters.add(converter);
        }
    }

    /**
     * 增加拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }


//    /**
//     * 跨域过滤器实例
//     * @return
//     */
//    @Bean
//    @ConditionalOnBean(CorsFilter.class)
//    public FilterRegistrationBean corsFilterRegistration(CorsFilter corsFilter) {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(corsFilter);
//        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

    /**
     * ip白名单过滤器实例
     * @return
     */
    @Bean
    @ConditionalOnBean(IpWhiteListFilter.class)
    public FilterRegistrationBean ipWhiteListFilterRegistration(IpWhiteListFilter ipWhiteListFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(ipWhiteListFilter);
        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 20000);
        return registrationBean;
    }

    /**
     * 参数签名校验过滤器实例
     * @return
     */
    @Bean
    @ConditionalOnBean(ParamsVerifyFilter.class)
    public FilterRegistrationBean aramsVerifyFilterRegistration(ParamsVerifyFilter paramsVerifyFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(paramsVerifyFilter);
        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 10000);
        return registrationBean;
    }

    /**
     * 创建参数校验validator
     * @return
     */
    @Bean
    public Validator validator () {
        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(
                        new LocaleContextMessageInterpolator(
                            new ResourceBundleMessageInterpolator(
                                    new MessageSourceResourceBundleLocator(messageSource)
                            )
                        )
                )
                .buildValidatorFactory()
                .getValidator();
        return validator;
    }


    @Bean
    public SpringApplicationContextHolder springApplicationContextHolder() {
        return new SpringApplicationContextHolder();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
