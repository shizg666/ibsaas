package com.landleaf.ibsaas.common.utils.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;


public class SMSjianzhoUtil {
	 public static String sms1 = "sdk_aihuike";
	 public static String sms2 = "06111198";
	 public static String newUrl = "http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http";
	 /**
	  * 调用此方法发送短信，私有方法。
	  * @param phones 手机号集，多个的话用英文分号隔开，如"12312312323;12312332121"。
	  * @param content 短信内容，不能出现。
	  * @return false发送失败。true 发送成功。
	  */
	 @Deprecated
	 public static int send(String phones,String content) {
		 String posturl = newUrl+"/sendBatchMessage";
		 String type = "application/x-www-form-urlencoded; charset=utf-8";
		 String body = "account="+sms1+"&password="+sms2+"&destmobile="+phones+"&msgText="+content+"【快速递】";
		 String result = doPost(posturl,body,type);
		 if(StringUtils.isEmpty(result)){
			 return -2;
		 }else if(Integer.parseInt(result) < 0){
			 return -2;
		 }else{
		 	return 0;
		 }
	 }

	/**
	 * 调用此方法发送短信
	 * @param account 账号信息
	 * @param password 密码信息
	 * @param url  第三方链接地址
	 * @param phones 手机号集，多个的话用英文分号隔开，如"12312312323;12312332121"。
	 * @param content 短信内容
	 * @return false发送失败。true 发送成功。
	 */
	public static int send(String account,String password,String url,String phones,String content) {
		String posturl = url+"/sendBatchMessage";
		String type = "application/x-www-form-urlencoded; charset=utf-8";
		String body = "account="+account+"&password="+password+"&destmobile="+phones+"&msgText="+content+"【快速递】";
		String result = doPost(posturl,body,type);
		if(StringUtils.isEmpty(result)){
			return -2;
		}else if(Integer.parseInt(result) < 0){
			return -2;
		}else{
			return 0;
		}
	}

	 /**
	  * 提交post请求。
	  * @param url 请求地址。
	  * @param body 请求内容。
	  * @param type 请求类型。
	  * @return post请求的返回值。
	  */
	 public static String doPost(String url,String body,String type){
		 //type = "application/x-www-form-urlencoded; charset=utf-8"
		 String response = "";
		 HttpClient client = new HttpClient();
		 PostMethod postMethod = new PostMethod(url);
		 postMethod.setRequestBody(body);
		 postMethod.setRequestHeader("Content-type", type) ;
		 try {
			 client.executeMethod(postMethod);
			 if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				 response = postMethod.getResponseBodyAsString();
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 return "0";
		 } finally {
			 postMethod.releaseConnection();
		 }
		 return response;
	 }
}
