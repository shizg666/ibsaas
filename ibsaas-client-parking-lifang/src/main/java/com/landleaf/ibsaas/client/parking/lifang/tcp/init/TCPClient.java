package com.landleaf.ibsaas.client.parking.lifang.tcp.init;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.tcp.code.TCPServerDecoder;
import com.landleaf.ibsaas.common.tcp.code.TCPServerEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component("TCPClient")
public class TCPClient {
    static final Logger LOGGER = LoggerFactory.getLogger(TCPClient.class);
    // 服务器ip地址
    @Value("${tcp.server.ip}")
    private String host;
    // 服务器端口
    @Value("${tcp.server.port}")
    private int port;

    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    @Autowired
    private TCPClientHandler tcpClientHandler;

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

    /**
     * 重连
     */
    public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        ChannelFuture f = null;
        try {
            if (bootstrap != null) {
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                bootstrap.remoteAddress(host, port);

                // 设置通道初始化
                bootstrap.handler(
                        new ChannelInitializer<SocketChannel>() {
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast("decoder", new TCPServerDecoder());
                                ch.pipeline().addLast("encoder", new TCPServerEncoder());
                                ch.pipeline().addLast(tcpClientHandler);
                            }
                        }
                );
                f = bootstrap.connect().addListener((ChannelFuture futureListener) ->
                {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        LOGGER.info("与服务端断开连接!在10s之后准备尝试重连!");
                        eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
                        initFalg = false;
                    } else {
                        initFalg = true;
                    }
                    if (initFalg) {
                        LOGGER.info("tcpClientHandler客户端连接成功!");

                        LOGGER.info(new Date() + ": 连接成功，启动控制台线程……");
                        channel = futureListener.channel();
                        TCPMessage data = new TCPMessage();
                        //改为外部配置
                        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
                        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
                        data.setMsgName(MsgTypeEnum.PARKING.name);
                        data.setSubMsgName(SubMsgTypeEnum.INIT_LINK.name);
                        String jsonBody = JSON.toJSONString(data);
                        LOGGER.info("停车业务TCP客户端断请求参数:{}", jsonBody);
                        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
                        ByteBuf firstMessage = Unpooled.buffer();
                        firstMessage.writeBytes(byteBody);
//                        channel.writeAndFlush(firstMessage);
                        channel.writeAndFlush(data);
                    }

                });

                // 阻塞
                f.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            LOGGER.info("客户端连接失败!" + e.getMessage());
        }

    }


}
