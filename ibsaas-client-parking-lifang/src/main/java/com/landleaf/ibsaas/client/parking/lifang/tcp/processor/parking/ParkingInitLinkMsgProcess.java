package com.landleaf.ibsaas.client.parking.lifang.tcp.processor.parking;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgServiceAnnotation;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 停车业务客户端初始连接
 */
@MsgServiceAnnotation(msgName = "parking", subMsgNames = {"init_link"})
public class ParkingInitLinkMsgProcess extends AbstractMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingInitLinkMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        /**
         * 1、这里只有一种是云服务端发起的初始连接响应
         */
        LOGGER.info("初始连接,服务端响应成功！！响应消息:{}", JSON.toJSONString(tcpMessage));

    }


}
