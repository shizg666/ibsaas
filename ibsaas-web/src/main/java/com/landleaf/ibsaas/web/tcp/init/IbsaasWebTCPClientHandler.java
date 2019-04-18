package com.landleaf.ibsaas.web.tcp.init;


import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import com.landleaf.ibsaas.web.tcp.processor.MsgListenerProcessor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@ChannelHandler.Sharable
@Component
public class IbsaasWebTCPClientHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOGGER = LoggerFactory.getLogger(IbsaasWebTCPClientHandler.class);

    @Autowired
    private MsgListenerProcessor msgListenerProcessor;
    @Autowired
    private IbsaasWebTCPClient ibsaasWebTCPClient;


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
        LOGGER.info("云TCP客户端 [time]:{},收到消息:{}", DateUtil.format(new Date()), JSON.toJSONString(tcpMessage));
        try {
            //放入缓存
            ConcurrentHashMapCacheUtils.setCache(tcpMessage.getMsgId(), tcpMessage, 60 * 1000L);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
//            ReferenceCountUtil.release(reseMsg);
        }


    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {

    }

    /**
     * 捕捉到异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        //重新初始化客户端
        ibsaasWebTCPClient.run();
    }


}
