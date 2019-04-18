package com.landleaf.ibsaas.common.tcp.code;

import com.google.protobuf.MessageLite;
import com.googlecode.protobuf.format.JsonFormat;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.proto.*;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * tcp server的编码器：将业务数据转换为协议格式
 *
 * @author dongzhiyong
 */
public class TCPServerEncoder extends MessageToByteEncoder<TCPMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TCPServerEncoder.class);

    private StringBuffer sbLogInfo = null;// 日志信息
    private StringBuffer moduleName = null;
    private StringBuffer serviceName = null;

    /**
     *
     */
    public TCPServerEncoder() {
        initLog();
    }

    private void initLog() {
        this.sbLogInfo = new StringBuffer();
        this.moduleName = new StringBuffer("TCPServerEncoder");
        this.serviceName = new StringBuffer("TCPServer");
    }

    /**
     * @param outboundMessageType
     */
    public TCPServerEncoder(Class<? extends TCPMessage> outboundMessageType) {
        super(outboundMessageType);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param preferDirect
     */
    public TCPServerEncoder(boolean preferDirect) {
        super(preferDirect);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param outboundMessageType
     * @param preferDirect
     */
    public TCPServerEncoder(Class<? extends TCPMessage> outboundMessageType, boolean preferDirect) {
        super(outboundMessageType, preferDirect);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.
     * ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, TCPMessage context, ByteBuf out) {
        if (context == null) {
            sbLogInfo.setLength(0);
            sbLogInfo.append("[ZKMockServerEncoder]context为空指针");
            LOGGER.error(sbLogInfo.toString());
        }

        String msgName = "";
        sbLogInfo.setLength(0);


        // 生成消息体
        String jsonBody = MessageUtil.getInstance().toJson(context);
        writeTCPMessage(out, jsonBody, context, msgName);
    }

    /**
     * 输出tcp消息到对端
     *
     * @param out
     * @param jsonBody
     */
    private void writeTCPMessage(ByteBuf out, String jsonBody, TCPMessage context, String msgName) {
        // 填写消息头
        short msgHeader = TCPMessageConstant.TCP_Msg_Header;
//        byte[] byteBody = jsonBody.getBytes(Charset.forName("utf-8"));
        byte[] byteBody = convertBeanToProtobuf(jsonBody, context);

        short msgLength = (short) byteBody.length;

        sbLogInfo.setLength(0);
        sbLogInfo.append("向客户端发送" + msgName + "(长度=" + msgLength + "):" + "," + jsonBody);
        LOGGER.error(sbLogInfo.toString());

        NumberUtil.writeBufferShort(out, msgHeader);
        NumberUtil.writeBufferShort(out, msgLength);
        out.writeBytes(byteBody);
    }

    //protobuf数据转java实体
    private byte[] convertBeanToProtobuf(String jsonBody, TCPMessage context) {
        byte[] byteBody = null;
        /*****************************************生成protobuf数据*****************************************************/

        byteBody = selectProtoConvert(context.getMsgName(), context.getSubMsgName(), jsonBody);
        /*****************************************生成protobuf数据end**************************************************/

        return byteBody;


    }

    private byte[] selectProtoConvert(String msgName, String subMsgName, String jsonBody) {

        ByteBuf byteBuf = null;
        try {
            if (StringUtil.isEquals(msgName, MsgTypeEnum.PARKING.name)) {
                if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.INIT_LINK.name)) {
                    BaseMsgProto.BaseMsg.Builder builder = BaseMsgProto.BaseMsg.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CHANNEL_LIST.name)) {
                    ChannelListQueryProto.ChannelListQuery.Builder builder = ChannelListQueryProto.ChannelListQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CHARGERULE_LIST.name)) {
                    ChargeruleListQueryProto.ChargeruleListQuery.Builder builder = ChargeruleListQueryProto.ChargeruleListQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_REAL_COUNT.name)) {
                    ParkingRealCountQueryProto.ParkingRealCountQuery.Builder builder = ParkingRealCountQueryProto.ParkingRealCountQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_RECORD.name)) {
                    UsercrdtmListQueryProto.UsercrdtmListQuery.Builder builder = UsercrdtmListQueryProto.UsercrdtmListQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_REAL_COUNT_HOUR.name)) {
                    ParkingRealCountFHourQueryProto.ParkingRealCountFHourQuery.Builder builder = ParkingRealCountFHourQueryProto.ParkingRealCountFHourQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CAR_DETAIL.name)) {
                    UserinfoDetailQueryProto.UserinfoDetailQuery.Builder builder = UserinfoDetailQueryProto.UserinfoDetailQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CAR_LIST.name)) {
                    UserinfoListQueryProto.UserinfoListQuery.Builder builder = UserinfoListQueryProto.UserinfoListQuery.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.HEART_BEAT.name)) {
                    HeartbeatProto.Heartbeat.Builder builder = HeartbeatProto.Heartbeat.newBuilder();
                    JsonFormat.merge(jsonBody, builder);
                    byteBuf = wrappedBuffer(((MessageLite) builder.build()).toByteArray());
                }
            }
        } catch (Exception e) {
            LOGGER.info(String.format("protobuf数据转字节异常:%s", e.getMessage(), e));
        }
        return ByteBufUtil.getBytes(byteBuf);
    }

}