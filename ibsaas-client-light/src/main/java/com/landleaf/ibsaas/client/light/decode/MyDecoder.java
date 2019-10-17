package com.landleaf.ibsaas.client.light.decode;

import com.landleaf.ibsaas.client.light.util.Hex;
import com.landleaf.ibsaas.client.light.util.SpringUtil;
import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        try{
            //创建字节数组,buffer.readableBytes可读字节长度
            byte[] b = new byte[buffer.readableBytes()];
            //复制内容到字节数组b
            buffer.readBytes(b);
            //字节数组转16字符串
            String str16 = Hex.bytesToHexString(b);
//            String str2 = new String(b,"utf8");
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+str2);


            //16进制06 代表ACK 成功 15 NCK失败
            //成功失败的返回不处理
            if ("06".equals(str16) || str16.startsWith("15")){
                return;
            }
            //The complete message is: <STX><Address><Type><Command><ETX>
            if ((!str16.startsWith("02")) || (!str16.endsWith("03"))){
                return;
            }
            String message = Hex.hexStr2Str(str16);
//            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+message);
            BuildResponse buildResponse2 = (BuildResponse) SpringUtil.getBean("buildResponse");
            LightMsgResponse lightMsgResponse = buildResponse2.getResponse(message);
            if (lightMsgResponse == null){
                return;
            }
            out.add(lightMsgResponse);
        }catch (Exception e){
            log.error("decode 处理消息报错：{}",e.getMessage());
        }

    }



}