package com.landleaf.ibsaas.web.tcp.schedule;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * 周期性心跳发送
 */
@Component
@Configurable
public class HeartBeatSchedule {



    @Scheduled(cron = "*/30 * * * * ? ")
    public void sendHeartBeat(){

        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.HEART_BEAT.name);
        HeartBeatDTO heartBeatDTO = new HeartBeatDTO();
        heartBeatDTO.setClientId(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId);
        heartBeatDTO.setClientInfo("云客户端");
        heartBeatDTO.setClientVersion("云客户端");
        data.setRequestBody(heartBeatDTO);
        String jsonBody = JSON.toJSONString(data);
        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
        ByteBuf firstMessage = Unpooled.buffer();
        firstMessage.writeBytes(byteBody);
        ChannelHandlerContext ctx = TcpConnectManager.getInstance().getCtx(TCPMessageSourceEnum.SERVER.clientId);
        if(ctx!=null){
            ctx.writeAndFlush(data);
        }


    }

}
