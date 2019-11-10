package com.landleaf.ibsaas.client.parking.lifang.tcp.processor;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.exception.parking.MsgErrorEnum;
import com.landleaf.ibsaas.common.exception.parking.MsgProcessorException;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 处理消息路由
 */
@Component
public class MsgListenerProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MsgListenerProcessor.class);

    @Autowired
    private Map<String, MsgProcessor> msgProcessorServiceMap;

    /**
     * 根据msgName路由，查找处理processor
     */
    public void process(String msgName , String subMsgName, TCPMessage tcpMessage, ChannelHandlerContext ctx) {

        MsgProcessor msgProcessor = selectProcessorService(msgName,subMsgName);

        try {
            if (msgProcessor == null) {
                logger.error(String.format("根据msgName:%s和子消息subMsgName：%s 没有找到对应的处理消息的服务", msgName,subMsgName));
                throw new MsgProcessorException(MsgErrorEnum.NOT_FOUND_PROCESSORSERVICE);
            }
            logger.info(String.format("根据[msgName]:%s 子消息[subMsgName]:%s 找到对应的处理消息的服务:", msgName, subMsgName,
                    msgProcessor.getClass().getName()));
            //调用该类的方法,处理消息
            msgProcessor.handle(tcpMessage,ctx);
        } catch (Exception e) {
            //异常暂不处理
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据topic和tag查询对应的具体的消费服务
     *
     * @param msgName 主消息
     * @param subMsgName 子消息
     */
    private MsgProcessor selectProcessorService(String msgName,String subMsgName) {
         MsgProcessor msgProcessor = null;
        for (Entry<String, MsgProcessor> entry : msgProcessorServiceMap.entrySet()) {
            //获取service实现类上注解的topic和tags
            MsgServiceAnnotation msgServiceAnnotation = entry.getValue().getClass().getAnnotation(MsgServiceAnnotation.class);
            if (msgServiceAnnotation == null) {
                logger.error("消息处理服务：" + entry.getValue().getClass().getName() + "上没有添加msgServiceAnnotation注解");
                continue;
            }
            String annotationMsgName = msgServiceAnnotation.msgName();
            List<String> annotationSubMsgName =Arrays.asList(msgServiceAnnotation.subMsgNames());

            //根据消息与子消息筛选
            if (!annotationMsgName.equals(msgName)||!annotationSubMsgName.contains(subMsgName)) {
                continue;
            }
            msgProcessor = entry.getValue();
            break;
        }
        return msgProcessor;
    }

}