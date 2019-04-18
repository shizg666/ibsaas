package com.landleaf.ibsaas.server.tcp.handler;


import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.server.tcp.processor.MsgListenerProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executor;

/**
 * 业务处理handler
 */
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    static final Logger LOGGER = LoggerFactory.getLogger(TCPServerHandler.class);

    private MsgListenerProcessor msgListenerProcessor;
    private Executor tcpServerHandlerExecutor;

    public TCPServerHandler(MsgListenerProcessor msgListenerProcessor, Executor tcpServerHandlerExecutor) {
        this.msgListenerProcessor = msgListenerProcessor;
        this.tcpServerHandlerExecutor = tcpServerHandlerExecutor;
    }

    /**
     * 转发消息
     *
     * @param ctx
     * @param msg
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //此模块的作用是转发消息
        //问题是转发到哪，依据是啥
        tcpServerHandlerExecutor.execute(() -> {
            asynChannelRead(ctx, msg);
        });
    }

    //异步处理消息
    private void asynChannelRead(ChannelHandlerContext ctx, Object msg) {

        TCPMessage tcpMessage = (TCPMessage) msg;
        //更新会话
        TcpConnectManager instance = TcpConnectManager.getInstance();
        instance.updateCtx(tcpMessage.getFrom(), ctx);

        try {
            LOGGER.info("中转服务端[time],{},收到消息:{}", DateUtil.format(new Date()), JSON.toJSONString(tcpMessage));
            String msgName = tcpMessage.getMsgName();
            String subMsgName = tcpMessage.getSubMsgName();
            msgListenerProcessor.process(msgName, subMsgName, tcpMessage, ctx);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
//            ReferenceCountUtil.release(reseMsg);
        }
    }

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        //无任何意义
        LOGGER.info("CHANNEL_ACTIVE " + ctx.channel().remoteAddress());

    }


}
