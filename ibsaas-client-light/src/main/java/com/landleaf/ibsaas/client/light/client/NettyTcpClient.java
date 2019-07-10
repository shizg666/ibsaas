package com.landleaf.ibsaas.client.light.client;

import com.landleaf.ibsaas.client.light.handle.netty.NettyTcpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component("NettyTcpClient")
@Slf4j
public class NettyTcpClient {
    static final Logger LOGGER = LoggerFactory.getLogger(NettyTcpClient.class);
    // 服务器ip地址
    @Value("${tcp.server.ip}")
    private String host;
    // 服务器端口
    @Value("${tcp.server.port}")
    private int port;

    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    @Autowired
    private NettyTcpClientHandler tcpClientHandler;

    private Channel channel;

    /**
     * 唯一标记
     */
    private boolean initFalg = true;

    /**
     * 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     * 都是AbstractBootstrap的子类。
     **/
    public void run() {
        doConnect(new Bootstrap(), group);
    }

    public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        ChannelFuture f = null;
        try {
            if (bootstrap != null) {
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                bootstrap.remoteAddress(host, port);
                bootstrap.handler(
                        new ChannelInitializer<SocketChannel>() {
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(tcpClientHandler);
                            }
                        }
                );
                f = bootstrap.connect().addListener((ChannelFuture future) ->
                {
                    final EventLoop eventLoop = future.channel().eventLoop();
                    if (future.isSuccess()) {
                        LOGGER.info(new Date() + ": 连接成功，启动控制台线程……");
                        channel = future.channel();
                    } else {
                        //失败重新连接
                        LOGGER.info(new Date() + ": 重试连接");
                        eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
                    }
                });
                // 阻塞
                f.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            LOGGER.info("客户端连接失败!" + e.getMessage());
        }finally {
        }

    }

    public Channel getChannel() {
        return channel;
    }
}
