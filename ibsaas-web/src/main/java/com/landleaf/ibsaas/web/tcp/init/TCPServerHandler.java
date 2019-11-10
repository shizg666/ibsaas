package com.landleaf.ibsaas.web.tcp.init;


import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.web.tcp.processor.MsgListenerProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
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

        TcpConnectManager instance = TcpConnectManager.getInstance();
        instance.updateCtx(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId, ctx);
        TCPMessage tcpMessage = (TCPMessage) msg;
        LOGGER.info("云TCP服务端 [time]:{},收到消息:{}", DateUtil.format(new Date()), JSON.toJSONString(tcpMessage));

        try {
            String msgName = tcpMessage.getMsgName();
            String subMsgName = tcpMessage.getSubMsgName();
            msgListenerProcessor.process(msgName, subMsgName, tcpMessage, ctx);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
//            ReferenceCountUtil.release(reseMsg);
        }

        try {

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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;

        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                eventType = "读空闲";
                ctx.channel().close();
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                // 不处理
                break;
            case ALL_IDLE:
                eventType ="读写空闲";
                // 不处理
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "超时事件：" +eventType);
    }


}
