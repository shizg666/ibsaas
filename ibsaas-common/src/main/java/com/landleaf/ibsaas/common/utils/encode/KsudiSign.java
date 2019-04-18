package com.landleaf.ibsaas.common.utils.encode;


public class KsudiSign {
	
	private static final String KEY = "~ksudi";
	private static final String TMSKEY = "%tms?~20170815~key";
	
	public static void main(String[] args){
	//	String data = "{\"runningnumber\":\"549742062845107\",\"flag\":2}";
/*		System.out.println(data);
		System.out.println(doSign(data, "29734ea4c030d5d5c6047399a266e013"));*/
	//	System.out.println(appsecret("test"));
//		System.out.println(EncryptMD5Util.encryptMD5("1502939596144" + "af51fe2f8569c3beb91625280e34bea7"));
//		System.out.println(doTmsSign("1502939596144","30001"));
	//	System.out.println(EncryptMD5Util.encryptMD5("nu=1234567890ce6c3e60d3edae6dd56dae0c0f71c576"));
		System.out.println(tmsSecret("20014"));


		
	}
	
	/**
	 * 快速签名
	 * @author huff
	 * @date 2016年6月22日
	 * @param content
	 * @param appid
	 * @return
	 */
	public static String doSign(String content,String appid) {
		String key = appsecret(appid);
	 	return EncryptMD5Util.encryptMD5(content + key);
	}
	/**
	 * 快递秘钥
	 * @author huff
	 * @date 2016年7月6日
	 * @param appid
	 * @return
	 */
	public static String appsecret(String appid) {
		return EncryptMD5Util.encryptMD5(EncryptMD5Util.encryptMD5(appid + KEY));
	}
	/**
	 * 快速签名(tms)
	 * @author jason
	 * @date 2017年08月15日
	 * @param content
	 * @param id
	 * @return
	 */
	public static String doTmsSign(String content,String id) {
		String key = tmsSecret(id);
	 	return EncryptMD5Util.encryptMD5(content + key);
	}
	/**
	 * 快递秘钥(tms)
	 * @author jason
	 * @date 2017年08月15日
	 * @param id
	 * @return
	 */
	public static String tmsSecret(String id) {
		return EncryptMD5Util.encryptMD5(EncryptMD5Util.encryptMD5(id + TMSKEY));
	}
}
