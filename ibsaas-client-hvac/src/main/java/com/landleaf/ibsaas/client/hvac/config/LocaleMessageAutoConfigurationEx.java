package com.landleaf.ibsaas.client.hvac.config;

import com.landleaf.ibsaas.client.hvac.util.LocaleMessageSourceEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * @description 消息国际化处理自动装载
 * @author wyl
 * @date 2019/3/21 0021 9:32
 * @version 1.0
*/
@Configuration
@EnableConfigurationProperties(LocaleMessagePropertiesEx.class)
@ConditionalOnProperty(prefix = "ibsaas.message", name = "enable", matchIfMissing = true)
public class LocaleMessageAutoConfigurationEx {


    @Autowired
    private LocaleMessagePropertiesEx localeMessagePropertiesEx;

    /**
     * 创建国际化资源
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 实例化自动以的国际化消息获取类
     */
    @Bean
    public LocaleMessageSourceEx localeMessageSource() {
        LocaleMessageSourceEx localeMessageSource = new LocaleMessageSourceEx();
        localeMessageSource.setMessageSource(messageSource());
        return localeMessageSource;
    }

    /**
     * 自动构建基于cookie的国际化处理类
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName(localeMessagePropertiesEx.getCookieLanguageNameKey());
        cookieLocaleResolver.setLanguageTagCompliant(true);
        if (StringUtils.isEmpty(localeMessagePropertiesEx.getDefaultCountry())
                || StringUtils.isEmpty(localeMessagePropertiesEx.getDefaultLang())) {
            cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
        } else {
            cookieLocaleResolver.setDefaultLocale(new Locale(localeMessagePropertiesEx.getDefaultLang(), localeMessagePropertiesEx.getDefaultCountry()));
        }
        cookieLocaleResolver.setCookieMaxAge(localeMessagePropertiesEx.getCookieMaxAge());
        return cookieLocaleResolver;
    }

}
