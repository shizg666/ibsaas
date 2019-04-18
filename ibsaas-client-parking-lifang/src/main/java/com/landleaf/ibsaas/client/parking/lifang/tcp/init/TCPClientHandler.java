package com.landleaf.ibsaas.client.parking.lifang.tcp.init;


import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgListenerProcessor;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    /**
     * 捕捉到异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        tcpClient.run();
    }


}
