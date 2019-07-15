package com.landleaf.ibsaas.client.light.util;

import org.springframework.stereotype.Component;

@Component
public class Hex {


    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }


    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static void main(String[] args) {
        String s = "\u0002R1S1!\u0003";
        String s1 = "R1S1!";
        String s2 = "02523153312103";
        String s3 = "06";
        byte[] d = {(byte)30,(byte)0,(byte)0Xc,(byte)0Xc,2,0,0,64,19, (byte)0X3e,(byte)0X3e,22,1,(byte)0X1e,(byte)0X3f};
        System.out.println("34转化成byte类型: " + str2HexStr(s));
        System.out.println("34转化成byte类型: " + str2HexStr(s1));
        System.out.println("34转化成byte类型: " + hexStr2Str(s2));
        System.out.println("34转化成byte类型: " + hexStr2Str(s3));
        System.out.println("34转化成byte类型0: " + s1.getBytes());
        System.out.println("34转化成byte类型0: " + s1.getBytes());
        System.out.println("34转化成byte类型1: " + str2HexStr(s1).getBytes());
    }
}
