package com.landleaf.ibsaas.web.tcp.processor.parking;

import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import com.landleaf.ibsaas.web.tcp.processor.AbstractMsgProcessor;
import com.landleaf.ibsaas.web.tcp.processor.MsgServiceAnnotation;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通道类型列表消息处理类
 */
@MsgServiceAnnotation(msgName = "parking",
        subMsgNames = {"channel_list",
                "chargerule_list",
                "parking_real_count",
                "parking_record",
                "car_list",
                "car_detail",
                "parking_real_count_hour",
                "parking_in_histroy"
})
public class ParkingMsgProcess extends AbstractMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingMsgProcess.class);

    //转发请求
    @Override
    public void handle(TCPMessage tcpMessage, ChannelHandlerContext ctx) {
        TcpConnectManager instance = TcpConnectManager.getInstance();
        String from = tcpMessage.getFrom();
        String to = tcpMessage.getTo();
        instance.updateCtx(from, ctx);
        try {
            //业务数据放入缓存
            ConcurrentHashMapCacheUtils.setCache(tcpMessage.getMsgId(), tcpMessage, 60 * 1000L);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
