package com.landleaf.ibsaas.rocketmq.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * 此注解用于标注消费者服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface MQConsumeService {
    /**
     * 消息主题
     */
     String topic();

    /**
     * 消息标签,如果是该主题下所有的标签，使用“*”
     */
     String[] tags();


}
