package com.landleaf.ibsaas.client.light.handle.light;

import com.landleaf.ibsaas.client.light.client.NettyPoolClient;
import com.landleaf.ibsaas.client.light.client.NettyTcpClient;
import com.landleaf.ibsaas.client.light.util.Hex;
import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
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

    @Autowired
    private NettyPoolClient nettyPoolClient;
    @Autowired
    private NettyTcpClient nettyTcpClient;

    public void process(LightMsg lightMsg){
        SimpleChannelPool pool = nettyPoolClient.poolMap.get(nettyPoolClient.getHost(lightMsg.getFloor()));
        Future<Channel> f = pool.acquire();
        f.addListener((FutureListener<Channel>) f1 -> {
            if (f1.isSuccess()) {
                Channel ch = f1.getNow();
                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                String command = this.getCommand(lightMsg);
                String msg = Hex.str2HexStr(command);
                log.info("AbstractLightHandler --------> process 发送灯控指令command：{},转化成16进制：{}",command,msg);
//                buffer.writeBytes(Hex.toBytes(mes));
//                ch.writeAndFlush(buffer);
//                pool.release(ch);
            }
        });
    }

    abstract public String getCommand(LightMsg lightMsg);

}
