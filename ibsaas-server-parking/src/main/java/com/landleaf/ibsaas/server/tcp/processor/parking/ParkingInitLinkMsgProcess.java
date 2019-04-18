package com.landleaf.ibsaas.server.tcp.processor.parking;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.DateUtil;
import com.landleaf.ibsaas.server.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.server.tcp.processor.MsgServiceAnnotation;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 停车业务客户端初始连接
 */
@MsgServiceAnnotation(msgName = "parking", subMsgNames = {"init_link"})
public class ParkingInitLinkMsgProcess extends AbstractMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingInitLinkMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        //两种处理
        /**
         * 1、一种是云服务端发起的初始连接请求
         * 2、另一种是停车业务数据服务端发起的初始连接请求
         */
        String from = tcpMessage.getFrom();
        TcpConnectManager instance = TcpConnectManager.getInstance();
        //连接应答消息
        TCPMessage sendData = new TCPMessage();
        sendData.setCreateTime(DateUtil.formatJavaDate(new Date()));
        sendData.setFrom(TCPMessageSourceEnum.SERVER.clientId);
        //谁来给谁
        sendData.setTo(from);
        sendData.setMsgId(tcpMessage.getMsgId());
        sendData.setMsgName(MsgTypeEnum.PARKING.name);
        sendData.setSubMsgName(SubMsgTypeEnum.INIT_LINK.name);
        Response response = new Response();
        response.setMessage("收到连接初始请求");
        response.setSuccess(true);
        sendData.setResponse(response);
        instance.writeAndFlush(sendData, ctx);

    }

}
