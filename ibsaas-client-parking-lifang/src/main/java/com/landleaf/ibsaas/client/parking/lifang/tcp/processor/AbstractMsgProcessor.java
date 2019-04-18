package com.landleaf.ibsaas.client.parking.lifang.tcp.processor;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有消息处理继承该类
 */
public abstract class AbstractMsgProcessor implements MsgProcessor {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMsgProcessor.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {

    }
}
