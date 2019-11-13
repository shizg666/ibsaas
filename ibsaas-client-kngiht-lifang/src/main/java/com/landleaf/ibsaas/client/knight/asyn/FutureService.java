package com.landleaf.ibsaas.client.knight.asyn;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.knight.domain.dto.KnightMessage;
import com.landleaf.ibsaas.client.knight.processor.MsgProcessor;
import com.landleaf.ibsaas.client.knight.rocketmq.KnightMqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class FutureService implements IFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);
    @Autowired
    private KnightMqProducer knightMqProducer;
    @Autowired
    private MsgProcessor msgProcessor;

    /**
     * 异步处理消息
     *
     * @param knightMessage
     */
    @Async
    public Future handlerMsg(KnightMessage knightMessage) {
        Future<KnightMessage> future = null;
        try {
            //处理消息
            KnightMessage result = msgProcessor.process(knightMessage.getMsgName(), knightMessage.getSubMsgName(), knightMessage);

            //返回处理结果
            knightMqProducer.sendMessageForWeb(JSON.toJSONString(result));

            future = (Future<KnightMessage>) new AsyncResult<KnightMessage>(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }
}
