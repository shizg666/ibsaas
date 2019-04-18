package com.landleaf.ibsaas.client.parking.lifang.tcp.processor.parking;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgServiceAnnotation;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通道类型列表消息处理类
 */
@MsgServiceAnnotation(msgName = "parking",
        subMsgNames = {"parking_real_count"})
@Component
public class ParkingRealCountMsgProcess extends AbstractMsgProcessor {

    @Autowired
    private IUsercrdtmService usercrdtmService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingRealCountMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        LOGGER.info("收到云TCP客户端发起的车位实时监控请求,{}", JSON.toJSONString(tcpMessage));
        //两种处理
        /**
         * 1、响应云端发起通道类型列表查询请求
         */
        TcpConnectManager instance = TcpConnectManager.getInstance();
        //组织查询
        UsercrdtmRealCountQueryDTO requestBody = JSON.parseObject(JSON.toJSONString(tcpMessage.getRequestBody()), UsercrdtmRealCountQueryDTO.class);
        ChannelListQueryDTO channelListQueryDTO = new ChannelListQueryDTO();
        channelListQueryDTO.setChannelType(ChannelTypeEnum.IN.name);
        UsercrdtmRealCountQueryDTO result = usercrdtmService.realCount(requestBody);
        Response response = new Response<>();
        response.setResult(result);
        //此时连接已建立 ，我要给inner发给消息
        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.PARKING_REAL_COUNT.name);
        data.setMsgId(tcpMessage.getMsgId());
        data.setResponse(response);
        instance.writeAndFlush(data, ctx);
    }

}
