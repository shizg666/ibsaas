package com.landleaf.ibsaas.client.parking.lifang.tcp.processor.parking;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgServiceAnnotation;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通道类型列表消息处理类
 */
@MsgServiceAnnotation(msgName = "parking",
        subMsgNames = {"parking_in_histroy"})
@Component
public class UsercrdtmInHistoryQueryMsgProcess extends AbstractMsgProcessor {

    @Autowired
    private IUsercrdtmService usercrdtmService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsercrdtmInHistoryQueryMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        LOGGER.info("收到云TCP客户端发起的车流量历史查询请求,{}", JSON.toJSONString(tcpMessage));
        /**
         * 1、响应云端发起车流量历史查询请求
         */
        TcpConnectManager instance = TcpConnectManager.getInstance();
        //组织查询
        UsercrdtmInHistoryQueryDTO requestBody = JSON.parseObject(JSON.toJSONString(tcpMessage.getRequestBody()), UsercrdtmInHistoryQueryDTO.class);
        List list = usercrdtmService.trafficFlow(requestBody);
        Response response = new Response<>();
        response.setResult(list);
        //此时连接已建立 ，我要给inner发给消息
        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.PARKING_IN_HISTROY.name);
        data.setMsgId(tcpMessage.getMsgId());
        data.setResponse(response);
        instance.writeAndFlush(data, ctx);
    }

}
