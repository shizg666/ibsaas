package com.landleaf.ibsaas.client.light.handle.netty;

import com.landleaf.ibsaas.client.light.decode.MyDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by YuQi on 2017/7/31.
 */
@Component
@Slf4j
public class NettyChannelPoolHandler implements ChannelPoolHandler {
    @Override
    public void channelReleased(Channel ch) throws Exception {
        log.info("channelReleased. Channel ID: " + ch.id());
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
        log.info("channelAcquired. Channel ID: " + ch.id());
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
//        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//        System.out.println("channelCreated. Channel ID: " + ch.id());
        SocketChannel channel = (SocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);
        channel.pipeline()
//                .addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
//                .addLast(new StringDecoder()).addLast(new StringEncoder()).addLast(new NettyClientHander());
                .addLast(new MyDecoder()).addLast(new NettyClientHander());

    }
}
