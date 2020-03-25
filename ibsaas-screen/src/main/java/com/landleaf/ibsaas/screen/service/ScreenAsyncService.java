package com.landleaf.ibsaas.screen.service;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.screen.enums.ScreenHvacEnum;
import com.landleaf.ibsaas.screen.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Lokiy
 * @date 2019/12/12 16:45
 * @description:
 */
@Service
@Slf4j
public class ScreenAsyncService {

    @Autowired
    @Qualifier("serviceTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    public Map<String, Object> asyncExecuteService()  {
        final Map<String, Object> result = new ConcurrentHashMap<>(16);
        try {
            final CountDownLatch latch = new CountDownLatch(ScreenHvacEnum.values().length);
            for (ScreenHvacEnum ero : ScreenHvacEnum.values()){
                Class<?> aClass = Class.forName(ero.getExecuteClassPath());
                Object bean = SpringUtil.getBean(aClass);
                Runnable task = () -> {
                    try {
                        Method method = ReflectUtil.getMethod(aClass, ero.getMethodName(), ero.getParams());
                        Object  invokeResult = method.invoke(bean);
                        result.put(ero.getType(), invokeResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("------------------------------>异步处理方法错误"+e.getMessage(), e);
                    }finally {
                        latch.countDown();
                    }
                };
                executor.execute(task);
            }
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>异步处理方法错误"+e.getMessage(), e);
        }
        return result;
    }
}
