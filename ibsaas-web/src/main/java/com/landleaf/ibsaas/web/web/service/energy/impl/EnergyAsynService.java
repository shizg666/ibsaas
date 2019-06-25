package com.landleaf.ibsaas.web.web.service.energy.impl;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.enums.energy.EnergyReportOverviewEnum;
import com.landleaf.ibsaas.web.web.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Lokiy
 * @date 2019/6/25 9:59
 * @description: 异步处理方法
 */
@Service
@Slf4j
public class EnergyAsynService {

    @Autowired
    @Qualifier("serviceTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    public Map<String, Object> asynExecuteService(EnergyReportExDTO energyReportDTO)  {
        final Map<String, Object> result = new ConcurrentHashMap<>(16);
        try {
            final CountDownLatch latch = new CountDownLatch(EnergyReportOverviewEnum.values().length);
            for (EnergyReportOverviewEnum ero : EnergyReportOverviewEnum.values()){
                Class<?> aClass = Class.forName(ero.getExecuteClassPath());
                Object bean = SpringUtil.getBean(aClass);
                Runnable task = () -> {
                    try {
                        Method method = ReflectUtil.getMethod(aClass, ero.getMethodName(), ero.getParams());
                        Object  invokeResult = method.invoke(bean, energyReportDTO);
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
