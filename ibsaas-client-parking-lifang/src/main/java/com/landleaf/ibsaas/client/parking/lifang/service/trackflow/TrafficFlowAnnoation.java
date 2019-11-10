package com.landleaf.ibsaas.client.parking.lifang.service.trackflow;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * 此注解用于标注车流量查询服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface TrafficFlowAnnoation {
    /**
     * 查询类型
     */
     String  code();



}
