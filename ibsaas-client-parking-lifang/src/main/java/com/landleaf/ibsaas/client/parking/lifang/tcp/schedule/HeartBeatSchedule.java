package com.landleaf.ibsaas.client.parking.lifang.tcp.schedule;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.tcp.init.TCPClientHandler;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 周期性心跳发送
 */
@Component
@Configurable
public class HeartBeatSchedule {
    static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatSchedule.class);



    @Scheduled(cron = "*/30 * * * * ? ")
    public void sendHeartBeat(){

        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.HEART_BEAT.name);
        HeartBeatDTO heartBeatDTO = new HeartBeatDTO();
        heartBeatDTO.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        heartBeatDTO.setClientInfo("停车业务系统--立方");
        heartBeatDTO.setClientVersion("停车业务系统--立方");
        data.setRequestBody(heartBeatDTO);
        String jsonBody = JSON.toJSONString(data);
        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
        ByteBuf firstMessage = Unpooled.buffer();
        firstMessage.writeBytes(byteBody);
        ChannelHandlerContext ctx = TcpConnectManager.getInstance().getCtx(TCPMessageSourceEnum.SERVER.clientId);
        LOGGER.info("发送心跳消息{}", DateUtil.format(new Date()));
        if(ctx!=null){
            ctx.writeAndFlush(data);
        }

    }

}
