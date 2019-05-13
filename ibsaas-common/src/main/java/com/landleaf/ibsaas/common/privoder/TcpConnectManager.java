package com.landleaf.ibsaas.common.privoder;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @董志勇 管理tcp连接，维护哈希表clientID->ctx
 * 当写操作命令通过EventBus失败时，调用本程序再次发送写命令
 */
public enum TcpConnectManager {
    //单实例
    INSTANCE;
    private final static Map<String, ChannelHandlerContext> tcpConnectMap = new ConcurrentHashMap<>();

    public static TcpConnectManager getInstance() {
        return INSTANCE;
    }

    TcpConnectManager() {

    }

    public void updateCtx(String clientID, ChannelHandlerContext ctx) {
        tcpConnectMap.put(clientID, ctx);
    }

    public void deleteCtx(String clientID, ChannelHandlerContext ctx) {
        tcpConnectMap.remove(clientID);
    }

    public ChannelHandlerContext getCtx(String clientID) {
        return tcpConnectMap.get(clientID);
    }



    public  void writeAndFlush(TCPMessage data, ChannelHandlerContext ctx) {
//        String jsonBody = JSON.toJSONString(data);
//        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
//        ByteBuf sendMessage = Unpooled.buffer();
//        sendMessage.writeBytes(byteBody);
//        ctx.writeAndFlush(sendMessage);
        ctx.writeAndFlush(data);
    }

}
