package com.landleaf.ibsaas.common.utils.encode;


import java.security.MessageDigest;

public class EncryptMD5Util {
	public static final String CHARSET = "utf-8";

	/**
	 * MD5加密
	 * @param str
	 * @return 16进制，小写的
	 * @author justin.li
	 * @date 2017年6月9日
	 */
	public static String encryptMD5(String str) {   
		MessageDigest md;
		   try {   
			   // 生成一个MD5加密计算摘要   
			   md = MessageDigest.getInstance("MD5");   
			   // 计算md5函数   
			   md.update(str.getBytes(CHARSET));   
			   byte[] result = md.digest();
			   StringBuffer sb = new StringBuffer(32);
			   for (int i = 0; i < result.length; i++) {
				    int val = result[i] & 0xff;
				    if (val <= 0xf) {
				        sb.append("0");
				    }
				    sb.append(Integer.toHexString(val));
			   }
			   return sb.toString().toLowerCase();
		   } catch (Exception e) {   
			   e.printStackTrace();   
		   }   
	   return str;   
	}


	/**
	 * 两次MD5加密
	 * @param str
	 * @return 16进制，小写的
	 * @author justin.li
	 * @date 2017年6月9日
	 */
	public static String encryptMD5twice(String str){
		String md5 = encryptMD5(str);
		md5 = encryptMD5(md5);
		return md5;
	}
	
	/**
     * 使用指定的编码方式进行MD5加密
     * @param str 内容       
     * @param charset 编码方式
	 * @return 16进制，小写的
	 * @throws Exception 
     */
	public static String encryptMD5(String str, String charset) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(str.getBytes(charset));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val <= 0xf) {
	            sb.append("0");
	        }
	        sb.append(Integer.toHexString(val));
	    }
	    return sb.toString().toLowerCase();
	}
	
}
