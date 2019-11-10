package com.landleaf.ibsaas.common.tcp.code;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.proto.*;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.SubMsgTypeEnum;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * tcp server的解码器：解析协议数据，并将其转换为业务数据
 *
 * @author dongzhiyong
 */
public class TCPServerDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCPServerDecoder.class);

    private StringBuffer sbLogInfo = new StringBuffer();// 日志信息
    private StringBuffer moduleName = new StringBuffer("TCPServerDecoder");
    private StringBuffer serviceName = new StringBuffer("TCPServer");
    private static final int MAX_DATA_SIZE = 2048 * 2;// bytebuf中数据量过大，无法处理，则丢弃

    public TCPServerDecoder() {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int readLength = 0;
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();// 先记下当前缓冲区的位置
        int beginIndex = in.readerIndex();
        try {
            // 1、读取消息头
            short msgHeader = NumberUtil.readBufferShort(in);
            if (msgHeader != TCPMessageConstant.TCP_Msg_Header) {
                sbLogInfo.setLength(0);
                sbLogInfo.append("消息头字段为无效值 = " + "0x" + Integer.toHexString(msgHeader));
                LOGGER.error(sbLogInfo.toString());
                ctx.close();
                return;
            }

            // 2、读取消息长度
            short msgLen = NumberUtil.readBufferShort(in);

            // 3、读取消息体
            readLength = in.readableBytes();
            if (readLength < msgLen) {
                in.resetReaderIndex();
                return;
            }
            byte[] byteBody = new byte[msgLen];
            in.readBytes(byteBody, 0, msgLen);

            // 设置新的readindex位置，准备读取下一条消息
            in.readerIndex(beginIndex + 2 + 2 + msgLen);

            TCPMessage tcpMessage = convertProtobufToBean(byteBody);


//            String body = new String(byteBody, "UTF-8");

            // 4、解析消息体
//            TCPMessage tcpMessage = JSON.parseObject(body, TCPMessage.class);
            if (tcpMessage != null) {
                // 只有解析正常的消息才能发给handler处理
                out.add(tcpMessage);
            }
            byteBody = null;
        } catch (Exception e) {
            sbLogInfo.setLength(0);
            sbLogInfo.append(e.getMessage());
            LOGGER.error(sbLogInfo.toString());
            e.printStackTrace();
        } finally {
            int readable = in.readableBytes();
            // 缓冲区数据过大
            if (readable > MAX_DATA_SIZE) {
                // 忽略所有可读的字节,使readindex和writeindex指向同一位置，使netty回收该内存
                in.skipBytes(readable);
                // 客户端重连
                ctx.close();
                sbLogInfo.setLength(0);
                sbLogInfo.append("bytebuf中的数据过多无法处理。丢弃数据的长度 = " + readable + "，当前缓冲区 = " + in.toString());
                LOGGER.error(sbLogInfo.toString());
            }
        }

    }

    //protobuf数据转java实体
    private TCPMessage convertProtobufToBean(byte[] byteBody) {
        TCPMessage context = null;
        String data = null;
        /*****************************************解析protobuf数据*****************************************************/
        try {
            //获取公共类型builder
            BaseMsgProto.BaseMsg.Builder builder = BaseMsgProto.BaseMsg.parseFrom(byteBody).toBuilder();
            String msgName = builder.getMsgName();
            String subMsgName = builder.getSubMsgName();

            data = selectProtoConvert(msgName, subMsgName, byteBody, context);

        } catch (InvalidProtocolBufferException e) {
            LOGGER.error(String.format("转换protobuf数据异常：%s", e.getMessage()), e);
        }

        try {
            //解析protobuf数据
            context = JSON.parseObject(data, TCPMessage.class);
        } catch (Exception e) {
            LOGGER.info(String.format("请求数据转实体异常:%s", e.getMessage(), e));
        }
        /*****************************************解析protobuf数据end**************************************************/

        return context;


    }

    private String selectProtoConvert(String msgName, String subMsgName, byte[] byteBody, TCPMessage context) {

        String data = null;
        try {
            if (StringUtil.isEquals(msgName, MsgTypeEnum.PARKING.name)) {
                if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.INIT_LINK.name)) {
                    BaseMsgProto.BaseMsg.Builder builder = BaseMsgProto.BaseMsg.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CHANNEL_LIST.name)) {
                    ChannelListQueryProto.ChannelListQuery.Builder builder = ChannelListQueryProto.ChannelListQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CHARGERULE_LIST.name)) {
                    ChargeruleListQueryProto.ChargeruleListQuery.Builder builder = ChargeruleListQueryProto.ChargeruleListQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_REAL_COUNT.name)) {
                    ParkingRealCountQueryProto.ParkingRealCountQuery.Builder builder = ParkingRealCountQueryProto.ParkingRealCountQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_RECORD.name)) {
                    UsercrdtmListQueryProto.UsercrdtmListQuery.Builder builder = UsercrdtmListQueryProto.UsercrdtmListQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_REAL_COUNT_HOUR.name)) {
                    ParkingRealCountFHourQueryProto.ParkingRealCountFHourQuery.Builder builder = ParkingRealCountFHourQueryProto.ParkingRealCountFHourQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CAR_DETAIL.name)) {
                    UserinfoDetailQueryProto.UserinfoDetailQuery.Builder builder = UserinfoDetailQueryProto.UserinfoDetailQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.CAR_LIST.name)) {
                    UserinfoListQueryProto.UserinfoListQuery.Builder builder = UserinfoListQueryProto.UserinfoListQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.HEART_BEAT.name)) {
                    HeartbeatProto.Heartbeat.Builder builder = HeartbeatProto.Heartbeat.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                } else if (StringUtil.isEquals(subMsgName, SubMsgTypeEnum.PARKING_IN_HISTROY.name)) {
                    UsercrdtmInHistoryQueryProto.UsercrdtmInHistoryQuery.Builder builder = UsercrdtmInHistoryQueryProto.UsercrdtmInHistoryQuery.parseFrom(byteBody).toBuilder();
                    data = com.googlecode.protobuf.format.JsonFormat.printToString(builder.build());
                }
            }
        } catch (InvalidProtocolBufferException e) {
            LOGGER.info(String.format("请求数据转json字符串异常:%s", e.getMessage(), e));
        }
        return data;
    }

}
