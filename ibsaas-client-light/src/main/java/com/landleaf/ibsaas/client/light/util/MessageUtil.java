package com.landleaf.ibsaas.client.light.util;

import com.landleaf.ibsaas.common.tcp.code.ModbusServerException;
import io.netty.buffer.ByteBuf;

public class MessageUtil {


    public static short readBufferShort(ByteBuf buffer,int length) throws ModbusServerException
    {
        byte[] dst = new byte[length];
        buffer.readBytes(dst);
        return byte2Short(dst);
    }

    /**
     * byte 数组与 short 的相互转换(低对低)
     *
     * @param b
     * @return
     * @throws ModbusServerException
     */
    public static short byte2Short(byte[] b) throws ModbusServerException {
        if (b.length == 1) {
            // ZK的公共数据字段定义目的端口的数据长度为1字节，这里做一下保护
            return (short) (b[0] & 0xFF);
        } else if (b.length == 2) {
            return (short) (b[0] & 0xFF | (b[1] & 0xFF) << 8);
        } else {
            throw new ModbusServerException("[NumberUtil]",
                    "byte数组长度错误，无法转换为short类型的数值,length = " + b.length);
        }
    }
}
