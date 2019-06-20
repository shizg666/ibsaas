package com.landleaf.ibsaas.web.web.service.energyflow;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * 此注解用于标注能耗曲线查询
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface EnergyFlowAnnoation {
    /**
     * 维度类型
     */
     String  code();



}
