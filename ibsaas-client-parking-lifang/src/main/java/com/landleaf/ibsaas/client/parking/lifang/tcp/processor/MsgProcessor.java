package com.landleaf.ibsaas.client.parking.lifang.tcp.processor;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理接口
 */
public interface MsgProcessor {

    void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx);
}
