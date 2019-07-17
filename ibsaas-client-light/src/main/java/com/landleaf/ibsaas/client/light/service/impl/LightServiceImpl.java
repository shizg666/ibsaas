package com.landleaf.ibsaas.client.light.service.impl;

import com.landleaf.ibsaas.client.light.client.NettyPoolClient;
import com.landleaf.ibsaas.client.light.handle.light.AbstractLightHandler;
import com.landleaf.ibsaas.client.light.handle.light.LightHandlerContext;
import com.landleaf.ibsaas.client.light.service.LightService;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.tcp.code.Hex;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LightServiceImpl implements LightService {

    @Autowired
    private NettyPoolClient nettyPoolClient;
//    @Autowired
//    private NettyTcpClient nettyTcpClient;

    @Autowired
    private LightHandlerContext lightHandlerContext;

//    public void controlLight(String code) {
//        SimpleChannelPool pool = nettyPoolClient.poolMap.get(nettyPoolClient.addr2);
//        Future<Channel> f = pool.acquire();
//        f.addListener((FutureListener<Channel>) f1 -> {
//            if (f1.isSuccess()) {
//                Channel ch = f1.getNow();
//                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//                buffer.writeBytes(Hex.toBytes(code));
//                ch.writeAndFlush(buffer);
//                pool.release(ch);
//            }
//        });
//    }

//    public void controlLight2(String code) {
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(Hex.toBytes(code));
//        Channel channel = nettyTcpClient.getChannel();
//        channel.writeAndFlush(buffer);
//    }

    @Override
    public void lightHandle(LightMsg lightMsg) {
        AbstractLightHandler lightHandler = lightHandlerContext.getLightHandler(lightMsg.getType());
        lightHandler.process(lightMsg);
    }

    @Override
    public void controlLight2(String code) {
        SimpleChannelPool pool = nettyPoolClient.poolMap.get(nettyPoolClient.getHost("3"));
        Future<Channel> f = pool.acquire();
        f.addListener((FutureListener<Channel>) f1 -> {
            if (f1.isSuccess()) {
                Channel ch = f1.getNow();
                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                buffer.writeBytes(Hex.toBytes(code));
                ch.writeAndFlush(buffer);
                pool.release(ch);
            }
        });
    }
}
