package com.landleaf.ibsaas.client.parking.lifang.mq.config.message;

import com.landleaf.ibsaas.client.parking.lifang.mq.message.LocaleMessageSource;
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
@EnableConfigurationProperties(LocaleMessageProperties.class)
@ConditionalOnProperty(prefix = "ibsaas.message", name = "enable", matchIfMissing = true)
public class LocaleMessageAutoConfiguration {


    @Autowired
    private LocaleMessageProperties localeMessageProperties;

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
    public LocaleMessageSource localeMessageSource() {
        LocaleMessageSource localeMessageSource = new LocaleMessageSource();
        localeMessageSource.setMessageSource(messageSource());
        return localeMessageSource;
    }

    /**
     * 自动构建基于cookie的国际化处理类
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName(localeMessageProperties.getCookieLanguageNameKey());
        cookieLocaleResolver.setLanguageTagCompliant(true);
        if (StringUtils.isEmpty(localeMessageProperties.getDefaultCountry())
                || StringUtils.isEmpty(localeMessageProperties.getDefaultLang())) {
            cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
        } else {
            cookieLocaleResolver.setDefaultLocale(new Locale(localeMessageProperties.getDefaultLang(), localeMessageProperties.getDefaultCountry()));
        }
        cookieLocaleResolver.setCookieMaxAge(localeMessageProperties.getCookieMaxAge());
        return cookieLocaleResolver;
    }

}
