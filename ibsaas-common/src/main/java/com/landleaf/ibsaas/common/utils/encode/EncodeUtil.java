package com.landleaf.ibsaas.common.utils.encode;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeUtil {
	
	public static final String key="okrwmvdnxq";
	/**
	 * base64解密
	 * @param data  base64加密后的信息
	 * @return
	 * @author jason.li
	 * @date 2015年10月26日 下午2:01:55
	 */
	public static String getFromBASE64(String data) {
		if (data == null)
			return null;
		BASE64Decoder base64 = new BASE64Decoder();
		try {
			byte[] b = base64.decodeBuffer(data);
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * base64加密  
	 * @author huff
	 * @date 2016年8月3日
	 * @param str
	 * @return
	 */
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);
        }  
        return s;  
    }  
	/**
     * 将指定编码字符串转换为base64编码
     * @param str 内容       
     * @param charset 原编码方式
	 * @return base64编码后的内容
	 * @author jin
	 * @date 2016年8月3日
     */
    public static String getBase64(String str, String charset){
		String result = null;
		try {
			result = Base64.encode(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;    
	}	
	/**
     * 将指定编码字符串转换为URLEncoder编码
     * @param str 内容       
     * @param charset 原编码方式
	 * @return URLEncoder编码后的内容
	 * @author jin
	 * @date 2016年8月3日
     */
    public static String getURLEncoder(String str, String charset){
		String result = null;
		try {
			result = URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 对手机号加密
	 * @param phone
	 * @return
	 * 2017年7月7日
	 * @author jason.li
	 */
	public static String encodePhone(String phone){
		if(StringUtils.isBlank(phone)) return phone;
		int length=phone.length();
		if(length<4) return phone;
		String preNumber=phone.substring(0,3);
		String appendNumber="";
		if(length>7) appendNumber=phone.substring(7);
		StringBuilder middleNumbeBulider=new StringBuilder();
		for(int i=3;i<phone.length();i++){
			if(i>6) break;
			String strNumber=phone.substring(i,i+1);
			try{
				Integer number=Integer.parseInt(strNumber);
				middleNumbeBulider.append(key.charAt(number));
			}catch(NumberFormatException e){
				middleNumbeBulider.append(strNumber);
			}
		}
		return preNumber+middleNumbeBulider.toString()+appendNumber;
	}
	/**
	 * 对手机号解密
	 * @param phone
	 * @return
	 * 2017年7月7日
	 * @author jason.li
	 */
	public static String decodePhone(String phone){
		for(int i=0;i<key.length();i++){
			phone=phone.replaceAll(key.charAt(i)+"",i+"");
		}
		return phone;
	}
	
	/**
	 * 对字符串进行脱敏处理
	 * @param word 被脱敏的字符
	 * @param startLength 被保留的开始长度 0代表不保留
	 * @param endLength 被保留的结束长度 0代表不保留
	 * @param pad 填充字符
	 * */
	public static String wordMask(String word, int startLength, int endLength,
			String pad) {
		if (word == null)
			return StringUtils.leftPad(word, startLength + endLength, pad);
		String startStr = "";
		String endStr = "";
		int padLength = 3;
		if (word.length() > startLength)
			startStr = StringUtils.substring(word, 0, startLength);
		if (word.length() > startLength + endLength)
			endStr = StringUtils.substring(word, word.length() - endLength);
		return startStr + StringUtils.repeat(pad, padLength) + endStr;
	}
}
