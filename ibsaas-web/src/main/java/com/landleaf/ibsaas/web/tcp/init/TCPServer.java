package com.landleaf.ibsaas.web.tcp.init;

import com.landleaf.ibsaas.common.tcp.code.TCPServerDecoder;
import com.landleaf.ibsaas.common.tcp.code.TCPServerEncoder;
import com.landleaf.ibsaas.web.tcp.processor.MsgListenerProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

@Component("TCPServer")
public class TCPServer {
    static final Logger LOGGER = LoggerFactory.getLogger(TCPServer.class);
    // 服务器端口
    @Value("${tcp.server.port}")
    private int tcpServerPort;

    @Autowired
    private MsgListenerProcessor msgListenerProcessor;
    @Autowired
    private Executor tcpServerHandlerExecutor;
    // 通过nio方式来接收连接和处理连接
    private static EventLoopGroup boss = new NioEventLoopGroup();
    private static EventLoopGroup work = new NioEventLoopGroup();

    // 启动引导器
    private static ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void run() {
        try {
            serverBootstrap.group(boss, work);
            // 设置nio类型的channel
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置监听端口
            serverBootstrap.localAddress(new InetSocketAddress(tcpServerPort));
            // 设置通道初始化
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个channel
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new TCPServerDecoder());
                    ch.pipeline().addLast("encoder", new TCPServerEncoder());
                    ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(60*5, 0, 0));
                    // pipeline管理channel中的Handler
                    // 在channel队列中添加一个handler来处理业务
                    ch.pipeline().addLast("tcpServerHandler", new TCPServerHandler(msgListenerProcessor, tcpServerHandlerExecutor));
                }
            });
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//默认的心跳间隔是7200s
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);// 禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);//使用对象池，重用缓冲区
            serverBootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
            // 配置完成，开始绑定server
            // 通过调用sync同步方法阻塞直到绑定成功
            ChannelFuture f = serverBootstrap.bind().sync();
            LOGGER.info(TCPServer.class.getName() +
                    " started and listen on " + f.channel().localAddress());
            // 监听服务器关闭事件
            // 应用程序会一直等待，直到channel关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
        } finally {
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

}
