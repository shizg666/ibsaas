package com.landleaf.ibsaas.client.parking.lifang.tcp.processor.parking;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.service.IUserinfoService;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.client.parking.lifang.tcp.processor.MsgServiceAnnotation;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoDetailQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
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
 * 车辆列表消息处理类
 */
@MsgServiceAnnotation(msgName = "parking",
        subMsgNames = {"car_detail"})
@Component
public class ParkingUserinfoMsgProcess extends AbstractMsgProcessor {

    @Autowired
    private IUserinfoService userinfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingUserinfoMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        LOGGER.info("收到云TCP客户端发起的车辆详情请求,{}", JSON.toJSONString(tcpMessage));
        //两种处理
        /**
         * 1、响应云端发起通道类型列表查询请求
         */
        TcpConnectManager instance = TcpConnectManager.getInstance();
        //组织查询
        UserinfoDetailQueryDTO requestBody = JSON.parseObject(JSON.toJSONString(tcpMessage.getRequestBody()), UserinfoDetailQueryDTO.class);
        UserinfoResponseDTO result = userinfoService.queryInfo(requestBody);
        Response response = new Response<>();
        response.setResult(result);
        //此时连接已建立 ，我要给inner发给消息
        TCPMessage data = new TCPMessage();
        //改为外部配置
        data.setFrom(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        data.setTo(TCPMessageSourceEnum.SERVER.clientId);
        data.setMsgName(MsgTypeEnum.PARKING.name);
        data.setSubMsgName(SubMsgTypeEnum.CAR_DETAIL.name);
        data.setMsgId(tcpMessage.getMsgId());
        data.setResponse(response);
        instance.writeAndFlush(data, ctx);
    }

}
