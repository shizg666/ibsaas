package com.landleaf.ibsaas.server.tcp.processor;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * 此注解用于标注消息处理服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface MsgServiceAnnotation {
    /**
     * 消息名称
     */
    String msgName();

    /**
     * 子消息名称
     */
    String[] subMsgNames();


}
