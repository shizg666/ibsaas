package com.landleaf.ibsaas.client.light.handle.netty;


import com.landleaf.ibsaas.client.light.client.NettyTcpClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
@Component("NettyTcpClientHandler")
public class NettyTcpClientHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOGGER = LoggerFactory.getLogger(NettyTcpClientHandler.class);


    @Autowired
    private NettyTcpClient nettyTcpClient;


    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            ctx.channel().id();
            LOGGER.info("返回***********************", byteBuf.toString(Charset.forName("utf-8")));
    }

    /**
     * 捕捉到异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        nettyTcpClient.run();
    }







}
