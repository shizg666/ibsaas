package com.landleaf.ibsaas.client.parking.lifang.mq.asyn;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingMessage;
import com.landleaf.ibsaas.client.parking.lifang.mq.processor.MsgProcessor;
import com.landleaf.ibsaas.client.parking.lifang.mq.rocketmq.ParkingMqProducer;
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
    private ParkingMqProducer parkingMqProducer;
    @Autowired
    private MsgProcessor msgProcessor;

    /**
     * 异步处理消息
     *
     * @param parkingMessage
     */
    @Async
    public Future handlerMsg(ParkingMessage parkingMessage) {
        Future<ParkingMessage> future = null;
        try {
            //处理消息
            ParkingMessage result = msgProcessor.process(parkingMessage.getMsgName(), parkingMessage.getSubMsgName(), parkingMessage);
            //返回处理结果
            parkingMqProducer.sendMessageForWeb(JSON.toJSONString(result));

            future = (Future<ParkingMessage>) new AsyncResult<ParkingMessage>(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }
}
