package com.landleaf.ibsaas.web.tcp.processor.parking;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.web.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.web.tcp.processor.MsgServiceAnnotation;
import com.landleaf.ibsaas.web.web.service.parking.ICommonParkingHeartBeatRecordService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 心跳周期性接收消息处理类
 */
@MsgServiceAnnotation(msgName = "parking",
        subMsgNames = {"heart_beat"})
@Component
public class ParkingHeartbeatMsgProcess extends AbstractMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingHeartbeatMsgProcess.class);

    @Autowired
    private ICommonParkingHeartBeatRecordService parkingHeartBeatRecordService;

    @Value("${tcp.heart.beat.interval}")
    private int interval;


    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        /**
         * 客户端周期性发送消息
         */
        try {
            HeartBeatDTO heartBeatDTO = JSON.parseObject(JSON.toJSONString(tcpMessage.getRequestBody()), HeartBeatDTO.class);
            heartBeatDTO.setInterval(interval);
            parkingHeartBeatRecordService.updateLatestedRecord(heartBeatDTO);
        } catch (Exception e) {
            LOGGER.error("心跳处理异常:{}",e.getMessage(),e);
        }


    }

}
