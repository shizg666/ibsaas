package com.landleaf.ibsaas.client.light.handle.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by YuQi on 2017/7/31.
 */
public class NettyClientHander extends ChannelInboundHandlerAdapter {
    static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        ctx.channel().id();
        System.out.println(ctx.channel().id()+"返回***********************"+count.getAndIncrement() + ":" + byteBuf.toString(Charset.forName("utf-8")));
    }
}
