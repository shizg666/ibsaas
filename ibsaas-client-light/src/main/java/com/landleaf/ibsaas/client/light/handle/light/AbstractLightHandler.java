package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.client.NettyPoolClient;
import com.landleaf.ibsaas.client.light.util.Hex;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractLightHandler {
    //02 16进制是ASCII控制字符
    private static final String START_COMMAND = "\u0002";
    //03 16进制是ASCII控制字符
    private static final String END_COMMAND = "\u0003";

    @Autowired
    private NettyPoolClient nettyPoolClient;

    public void process(LightMsg lightMsg){
        SimpleChannelPool pool = nettyPoolClient.poolMap.get(nettyPoolClient.getHost(lightMsg.getFloor()));
        Future<Channel> f = pool.acquire();
        f.addListener((FutureListener<Channel>) f1 -> {
            if (f1.isSuccess()) {
                Channel ch = f1.getNow();
                log.info("*********************************************************获取channel成功Id：{}，message：{}",ch.id(),lightMsg.toString());
                try {
                    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                    //02 16进制是ASCII控制字符
                    StringBuilder commandStr = new StringBuilder();
                    String command = this.getCommand(lightMsg);
                    commandStr.append(START_COMMAND).append(command).append(END_COMMAND);
                    String msg = Hex.str2HexStr(commandStr.toString());
                    log.info("AbstractLightHandler --------> process 发送灯控指令command：{},转化成16进制：{}",commandStr.toString(),msg);
                    buffer.writeBytes(Hex.toBytes(msg));
                    ch.writeAndFlush(buffer);
//                    ChannelFuture lastWriteFuture = null;
//                    lastWriteFuture = ch.writeAndFlush(buffer);
//
//                    // Wait until all messages are flushed before closing the channel.
//                    if (lastWriteFuture != null) {
//                        try {
//                            lastWriteFuture.sync();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }finally {
//                    Thread.sleep(6000);
                    log.info("*********************************************************释放 channel成功Id：",ch.id());
                    pool.release(ch);
                }
            }
        });
    }

    abstract public String getCommand(LightMsg lightMsg);

}
