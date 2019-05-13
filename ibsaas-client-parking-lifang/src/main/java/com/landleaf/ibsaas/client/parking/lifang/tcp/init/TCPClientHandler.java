package com.landleaf.ibsaas.client.parking.lifang.tcp.init;


import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgListenerProcessor;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
@Component("TCPClientHandler")
public class TCPClientHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOGGER = LoggerFactory.getLogger(TCPClientHandler.class);
    @Autowired
    private MsgListenerProcessor msgListenerProcessor;

    @Autowired
    private TCPClient tcpClient;

    /**
     * 此方法会在连接到服务器后被调用
     */

    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info(new Date() + "来吧---------");
        //周期性心跳
        ping(ctx.channel(),ctx);
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TcpConnectManager instance = TcpConnectManager.getInstance();
        instance.updateCtx(TCPMessageSourceEnum.SERVER.clientId, ctx);
        TCPMessage tcpMessage = (TCPMessage) msg;
        try {
            LOGGER.info("停车业务TCP客户端[time]:{},收到消息:{}", DateUtil.format(new Date()), JSON.toJSONString(tcpMessage));
            String msgName = tcpMessage.getMsgName();
            String subMsgName = tcpMessage.getSubMsgName();
            msgListenerProcessor.process(msgName, subMsgName, tcpMessage, ctx);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
//            ReferenceCountUtil.release(reseMsg);
        }
    }



    private void ping(Channel channel,ChannelHandlerContext ctx) {
        int second = 60;
        System.out.println("next heart beat will send after " + second + "s.");
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(ctx.channel().id()+"==="+channel.id());
                if (channel.isActive()) {
                    System.out.println("sending heart beat to the server...");
                   sendHeartBeat(channel);
                } else {
                    System.err.println("The connection had broken, cancel the task that will send a heart beat.");
                    channel.closeFuture();
                    ctx.close();
                    tcpClient.run();
                }
            }
        }, second, TimeUnit.SECONDS);

        future.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel,ctx);
                }
            }
        });
    }

    /**
     * 捕捉到异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        tcpClient.run();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // write heartbeat to server
                sendHeartBeat(ctx.channel());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }



    public void sendHeartBeat(Channel channel) {

        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.HEART_BEAT.name);
        HeartBeatDTO heartBeatDTO = new HeartBeatDTO();
        heartBeatDTO.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        heartBeatDTO.setClientInfo("停车业务系统--立方");
        heartBeatDTO.setClientVersion("停车业务系统--立方");
        data.setRequestBody(heartBeatDTO);
        String jsonBody = JSON.toJSONString(data);
        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
        ByteBuf firstMessage = Unpooled.buffer();
        firstMessage.writeBytes(byteBody);
        LOGGER.info("发送心跳消息{}", DateUtil.format(new Date()));
        channel.writeAndFlush(data);

    }


}
