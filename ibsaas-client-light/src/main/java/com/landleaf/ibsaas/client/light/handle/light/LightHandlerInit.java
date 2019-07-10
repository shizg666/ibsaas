package com.landleaf.ibsaas.client.light.handle.light;

import cn.hutool.core.lang.ClassScaner;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 灯光处理器初始化
 */
@Component
public class LightHandlerInit implements BeanFactoryPostProcessor {
    private static final String HANDLER_PACKAGE = "com.landleaf.ibsaas.client.light.handle.light";
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<String,Class> handlerMap = Maps.newHashMapWithExpectedSize(2);
        ClassScaner.scanPackageByAnnotation(HANDLER_PACKAGE,HandlerType.class).forEach(clazz->{
            String type = clazz.getAnnotation(HandlerType.class).type();
            handlerMap.put(type,clazz);
        });
        LightHandlerContext lightHandlerContext = new LightHandlerContext(handlerMap);
        configurableListableBeanFactory.registerSingleton(LightHandlerContext.class.getName(),lightHandlerContext);
    }
}
