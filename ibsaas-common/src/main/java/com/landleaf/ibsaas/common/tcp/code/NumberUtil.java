package com.landleaf.ibsaas.common.tcp.code;

import io.netty.buffer.ByteBuf;

import java.math.BigInteger;

/**数据操作工具列
 * @author dongzhiyong
 *
 */
public class NumberUtil {

	public static void main(String[] args) throws ModbusServerException {
		short aaaa;
		int intq;
		
		byte[] add = NumberUtil.short2Byte((short)0xBADA);
		aaaa = (short)0xff00;
		intq = short2UnsignInt(aaaa);
		System.out.println("intq = " + intq);
		int wangning = 34;
		System.out.println("34转化成byte类型: " + HexData.hexToString((NumberUtil.int2Byte(wangning))));
		
		// 测试int
		int a = 0x01020304;
		byte[] aa = int2Byte(a);
		int aaa = byte2Int(aa);

		// 测试long
		byte[] a8 = new byte[8];
		a8[0] = 0x01;
		a8[1] = 0x02;
		a8[2] = 0x03;
		a8[3] = 0x04;
		a8[4] = 0x05;
		a8[5] = 0x06;
		a8[6] = 0x07;
		a8[7] = 0x08;
		long bb = byte2Long(a8);
		byte[] bbb = long2Byte(bb);
		System.out.println(bbb);

		// 测试short
		byte[] c = new byte[2];
		c[0] = (byte)0x01;
		c[1] = (byte)0xff;
		byte[] ok = new byte[1];
		ok[0] = (byte)0xff;
		int ddww = byte2UnsignInt(c);
		System.out.println("dddww1 = " + ddww);
		
		short cc = byte2Short(c);
		byte[] ccc = short2Byte(cc);

		byte[] addr = new byte[4];
		addr[0] = 127;
		addr[1] = 0;
		addr[2] = 1;
		addr[3] = 2;
		String ipv4String = byte2ipByBig(addr);
		// System.out.println(ipv4String);

		byte[] addr2 = new byte[16];
		for (int k = 0; k < 16; k++) {
			addr2[k] = (byte) k;
		}
		String ipv6String = byte2ipByBig(addr2);
		System.out.println(ipv6String);

		String ipv4 = "127.255.1.1", ipv6 = "fe80:0:0:0:d48a:e3be:7d9d:ffff";
		// IPV4 all 4*8 =32 bit
		// java int is 32 bit but cann't store this.(it's signed.)
		long k = ipv42long(ipv4);
		System.out.println(k);
		System.out.println(Long.toBinaryString(k));
		System.out.println(long2ipv4(k));

		System.out.println("ipv6 start :" + ipv6);
		BigInteger big = ipv6toInt(ipv6);
		System.out.println(big.toString(16));
		System.out.println(int2ipv6(big));

		ipv6 = "fe80:0:0:0:d48a:e3be::ffff";
		System.out.println("ipv6 start :" + ipv6);
		big = ipv6toInt(ipv6);
		System.out.println(big.toString(16));
		System.out.println(int2ipv6(big));

	}
    
	/**将int数值写到buffer(低对低)
	 * @param buffer
	 * @param data
	 */
	public static void writeBufferInt(ByteBuf buffer, int data)
    {
        byte[] src = int2Byte(data);
		buffer.writeBytes(src);		
    }
	
	/**将short数值写到buffer(低对低)
	 * @param buffer
	 * @param data
	 */
	public static void writeBufferShort(ByteBuf buffer, short data)
    {
        byte[] src = short2Byte(data);
		buffer.writeBytes(src);		
    }
	
	/**将long数值写到buffer(低对低)
	 * @param buffer
	 * @param data
	 */
	public static void writeBufferLong(ByteBuf buffer, long data)
    {
        byte[] src = long2Byte(data);
		buffer.writeBytes(src);		
    }
	
	/**
	 * Z2协议专用
	 * 从buffer读取4byte(低对低)
	 * @param buffer
	 * @return
	 * @throws HHException 
	 */
	public static byte[] readBuffer4Byte(ByteBuf buffer) throws ModbusServerException
	{
		byte[] dst = new byte[4];
		buffer.readBytes(dst);
		return dst;
	}
    
	/**从buffer读取short(低对低)
	 * @param buffer
	 * @return
	 * @throws HHException 
	 */
	public static short readBufferShort(ByteBuf buffer) throws ModbusServerException
	{
		byte[] dst = new byte[2];
		buffer.readBytes(dst);
		return byte2Short(dst);
	}
	
	/**从buffer读取int(低对低)
	 * @param buffer
	 * @return
	 */
	public static int readBufferInt(ByteBuf buffer)
	{
		byte[] dst = new byte[4];
		buffer.readBytes(dst);
		return byte2Int(dst);
	}
	
	/**从buffer读取long(低对低)
	 * @param buffer
	 * @return
	 */
	public static long readBufferLong(ByteBuf buffer)
	{
		byte[] dst = new byte[8];
		buffer.readBytes(dst);
		return byte2Long(dst);
	}
	
	public static int byte2Int_1(byte b) {
		// Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
		return b & 0xFF;
	}

	/**
	 * byte 数组与 int 的相互转换(低对低)
	 * 
	 * @param b
	 * @return
	 */
	public static int byte2Int(byte[] b) {
		return b[0] & 0xFF | (b[1] & 0xFF) << 8 | (b[2] & 0xFF) << 16
				| (b[3] & 0xFF) << 24;
	}
	
	/**
	 * byte 数组与 int 的相互转换(哈尔滨)
	 * 
	 * @param b
	 * @return
	 */
	public static int byte2IntHEB(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16
				| (b[0] & 0xFF) << 24;
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

	/**
	 * byte 数组与 short 的相互转换(低对低)
	 * 
	 * @param b
	 * @return
	 * @throws ModbusServerException
	 */
	public static int byte2UnsignInt(byte[] b) throws ModbusServerException {
		int value;
		
		if (b.length == 1) {			
			value = 0x000000FF & b[0];	
		} else if (b.length == 2) {
			value =0x0000FFFF& (b[0] & 0xFF | (b[1] & 0xFF) << 8);
		
		} else {
			throw new ModbusServerException("[NumberUtil]",
					"byte数组长度错误，无法转换为short类型的数值,length = " + b.length);
		}
		return value;
	}
	public static short byte2Short(byte b0, byte b1) {
		return (short) (b0 & 0xFF | (b1 & 0xFF) << 8);
	}

	/**
	 * short转换为byte[](低对低)
	 * 
	 * @param number
	 * @return
	 */
	public static byte[] short2Byte(short number) {
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**有符号short数转换为无符号int
	 * @param number
	 * @return
	 */
	public static int short2UnsignInt(short number)
	{
		return (number & 0x0000ffff);
	}
	/**
	 * byte 数组与 int 的相互转换(低对低)
	 * 
	 * @param b
	 * @param pos
	 * @return
	 */
	public static int byte2Int(byte[] b, int pos) {
		return b[0 + pos] & 0xFF | (b[1 + pos] & 0xFF) << 8
				| (b[2 + pos] & 0xFF) << 16 | (b[3 + pos] & 0xFF) << 24;
	}

	/**
	 * int 转换为byte[]低对低
	 * 
	 * @param res
	 * @return
	 */
	public static byte[] int2Byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

	// byte 数组与 long 的相互转换(低对低)
	public static byte[] long2Byte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * byte[]转换为long(低对低)
	 * 
	 * @param b
	 * @return
	 */
	public static long byte2Long(byte[] b,int pos) {
		long s = 0;
		long s0 = b[0+pos] & 0xff;// 最低位
		long s1 = b[1+pos] & 0xff;
		long s2 = b[2+pos] & 0xff;
		long s3 = b[3+pos] & 0xff;
		long s4 = b[4+pos] & 0xff;// 最低位
		long s5 = b[5+pos] & 0xff;
		long s6 = b[6+pos] & 0xff;
		long s7 = b[7+pos] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * byte[]转换为long(低对低)
	 * 
	 * @param b
	 * @return
	 */
	public static long byte2Long(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}
	
	public static long ipv42long(String ipv4) {

		String[] splits = ipv4.split("\\.");
		long l = 0;
		l = l + (Long.valueOf(splits[0], 10)) << 24;
		l = l + (Long.valueOf(splits[1], 10) << 16);
		l = l + (Long.valueOf(splits[2], 10) << 8);

		l = l + (Long.valueOf(splits[3], 10));
		System.out.println(Long.toBinaryString(l));
		return l;
	}

	/**
	 * byte[]数组转换为ipv4/6字符串,addr按网络字节序编码
	 * 
	 * @param num
	 * @return
	 * @throws ModbusServerException
	 */
	public static String byte2ipByBig(byte[] addr) throws ModbusServerException {
		String ip = "";
		if (addr.length == 4) {
			for (int i = 0; i < addr.length; i++) {
				ip += (addr[i] & 0xFF) + ".";
			}
		} else if (addr.length == 16) {
			// ipv6
			for (int i = 0; i < addr.length; i++) {
				ip += (addr[i] & 0xFF) + ":";
			}
		} else {
			throw new ModbusServerException("[byteArray2ipString]",
					"ip数组长度不合法，无法转换ip数组为字符串");
		}
		return ip.substring(0, ip.length() - 1);
	}

	/**
	 * byte[]数组转换为ipv4字符串,addr按网络字节序编码
	 * 
	 * @param num
	 * @return
	 * @throws ModbusServerException
	 */
	public static String byte2ipv4ByBig(byte[] addr, int index)
			throws ModbusServerException {
		String ip = "";
		if (index + 3 > addr.length) {
			throw new ModbusServerException("[byte2ipv4ByBig]", "ip数组长度不合法，无法转换ip数组为字符串");
		}
		for (int i = 0; i < 4; i++) {
			ip += (addr[index + i] & 0xFF) + ".";
		}
		return ip.substring(0, ip.length() - 1);
	}

	public static String long2ipv4(long l) {
		String ip = "";
		ip = ip + (l >>> 24);

		ip = ip + "." + ((0x00ffffff & l) >>> 16);
		ip = ip + "." + ((0x0000ffff & l) >>> 8);
		ip = ip + "." + (0x000000ff & l);
		return ip;
	}

	public static BigInteger ipv6toInt(String ipv6) {

		int compressIndex = ipv6.indexOf("::");
		if (compressIndex != -1) {
			String part1s = ipv6.substring(0, compressIndex);
			String part2s = ipv6.substring(compressIndex + 1);
			BigInteger part1 = ipv6toInt(part1s);
			BigInteger part2 = ipv6toInt(part2s);
			int part1hasDot = 0;
			char ch[] = part1s.toCharArray();
			for (char c : ch) {
				if (c == ':') {
					part1hasDot++;
				}
			}
			// ipv6 has most 7 dot
			return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
		}
		String[] str = ipv6.split(":");
		BigInteger big = BigInteger.ZERO;
		for (int i = 0; i < str.length; i++) {
			// ::1
			if (str[i].isEmpty()) {
				str[i] = "0";
			}
			big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16))
					.shiftLeft(16 * (str.length - i - 1)));
		}
		return big;
	}

	public static String int2ipv6(BigInteger big) {
		String str = "";
		BigInteger ff = BigInteger.valueOf(0xffff);
		for (int i = 0; i < 8; i++) {
			str = big.and(ff).toString(16) + ":" + str;

			big = big.shiftRight(16);
		}
		// the last :
		str = str.substring(0, str.length() - 1);

		return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
	}
}
