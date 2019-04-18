package com.landleaf.ibsaas.web.tcp.init;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @董志勇 管理tcp连接，维护哈希表clientID->ctx
 * 当写操作命令通过EventBus失败时，调用本程序再次发送写命令
 */
public enum IbsaasWebTcpConnectManager {
    //单实例
    INSTANCE;
    private final static Map<String, ChannelHandlerContext> tcpConnectMap = new ConcurrentHashMap<>();

    public static IbsaasWebTcpConnectManager getInstance() {
        return INSTANCE;
    }

    IbsaasWebTcpConnectManager() {

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


}
