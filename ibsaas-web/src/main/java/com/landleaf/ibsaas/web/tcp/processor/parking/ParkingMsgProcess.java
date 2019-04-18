package com.landleaf.ibsaas.web.tcp.processor.parking;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.enums.parking.TCPMessageSourceEnum;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.web.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.web.tcp.processor.MsgServiceAnnotation;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通道类型列表消息处理类
 */
@MsgServiceAnnotation(msgName = "停车业务相关消息",
        subMsgNames = {"通道类型列表",
                "收费类型列表",
                "车位实时数量",
                "进出记录列表",
                "车辆列表",
                "车辆详情"
})
public class ParkingMsgProcess extends AbstractMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingMsgProcess.class);

    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        //两种处理
        /**
         * 1、一种是云服务端发起的初始连接请求
         * 2、另一种是停车业务数据服务端发起的初始连接请求
         */
        String from = tcpMessage.getFrom();
        String to = tcpMessage.getTo();
        if (from.equals(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.name)) {
            //云客户端====》转发到停车业务客户端
            handleFromCloudClient(tcpMessage, ctx);

        } else if (from.equals(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.name)) {
            //停车业务客户端===响应到云客户端
            handleFromDataClient(tcpMessage, ctx);
        }

    }

    private void handleFromCloudClient(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        //转发请求到停车业务客户端
        TcpConnectManager instance = TcpConnectManager.getInstance();
        instance.updateCtx(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.GET_TYPE, ctx);
        //现暂写死，后面根据clientId查找会话
        ChannelHandlerContext clientCtx = instance.getCtx(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.GET_TYPE);
        if (clientCtx == null) {
            throw new RuntimeException("未找到停车业务端相关会话");
        }
        instance.writeAndFlush(tcpMessage, clientCtx);
    }

    private void handleFromDataClient(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        //转发响应到云客户端
        TcpConnectManager instance = TcpConnectManager.getInstance();
        instance.updateCtx(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.GET_TYPE, ctx);
        ChannelHandlerContext clientCtx = instance.getCtx(TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.GET_TYPE);
        if (clientCtx == null) {
            throw new RuntimeException("未找到云客户端相关会话");
        }
        instance.writeAndFlush(tcpMessage, clientCtx);
    }
}
