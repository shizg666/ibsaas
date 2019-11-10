package com.landleaf.ibsaas.common.utils.encode;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.lang.reflect.Method;


public class D2DUncode {

	private static byte[] DESIV = { 
		(byte)0xEF,
		(byte)0xAB, 
		(byte)0x56, 
		(byte)0x78, 
		(byte)0x90,  
        (byte)0x34, 
        (byte)0xCD,
        (byte)0x12 };
	
	public static void main(String[] args) throws Exception {
        System.out.println(EncryptDES("qwe", "12345678"));
        System.out.println(DecryptDES(EncryptDES("qwe", "12345678"), "12345678"));
	}

	/**
	 * DES加密
	 * @param str      待加密字符串
	 * @param key    密钥
	 * @return           加密后的字符串
	 * @throws Exception
	 */
	public static String EncryptDES(String str, String key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(DESIV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return encodeBase64(cipher.doFinal(str.getBytes("UTF-8")));
	}
	
	/**
	 * 解密DES
	 * @param str      待解密字符串
	 * @param key    密钥
	 * @return           解密后的字符串
	 * @throws Exception
	 */
	public static String DecryptDES(String str, String key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(DESIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        return new String(cipher.doFinal(decodeBase64(str)));
	}
	
	/**
	 * Base64编码
	 * @param input
	 * @return
	 * @throws Exception
	 */
	 public static String encodeBase64(byte[]input) throws Exception{  
	        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
	        Method mainMethod= clazz.getMethod("encode", byte[].class);  
	        mainMethod.setAccessible(true);  
	         Object retObj=mainMethod.invoke(null, new Object[]{input});  
	         return (String)retObj;  
	    } 
		
	 /**
	  * Base64解码
	  * @param input
	  * @return
	  * @throws Exception
	  */
		public static byte[] decodeBase64(String input) throws Exception{  
	        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
	        Method mainMethod= clazz.getMethod("decode", String.class);  
	        mainMethod.setAccessible(true);  
	         Object retObj=mainMethod.invoke(null, input);  
	         return (byte[])retObj;  
	    }
}