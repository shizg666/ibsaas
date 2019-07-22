package com.landleaf.ibsaas.client.light.handle.netty;

import com.landleaf.ibsaas.client.light.handle.asy.AsyHandle;
import com.landleaf.ibsaas.client.light.handle.light.LightHandlerContext;
import com.landleaf.ibsaas.client.light.handle.light.reponse.LightResponse;
import com.landleaf.ibsaas.client.light.util.SpringUtil;
import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class NettyClientHander extends ChannelInboundHandlerAdapter {
//    static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LightMsgResponse lightMsgResponse = (LightMsgResponse) msg;
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        lightMsgResponse.setAddress(socketAddress.getAddress().toString());
        log.info("接收到消息：chnnelId: {},comment: {}",ctx.channel().id(),msg.toString());
        AsyHandle asyHandle = (AsyHandle) SpringUtil.getBean("asyHandle");
        asyHandle.handle(lightMsgResponse);
    }


}
