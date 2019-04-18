package com.landleaf.ibsaas.web.web.dto;

import org.apache.commons.codec.binary.Base64;

/**
 * @Title TokenMarshal
 * @Description 令牌序列化与反序列化
 */
final public class TokenMarshal {
	private TokenMarshal(){
	}

	/**
	 * 序列化(Base64编码)
	 * @param token
	 */
	public static String marshal(CookieToken token) {
		return new String(Base64.encodeBase64String(token.toBytes()));
	}

	/**
	 * 反序列化（Base64解码）
	 * @param tokenStr
	 */
	public static CookieToken unMarshal(String tokenStr) {
		return new CookieToken(Base64.decodeBase64(tokenStr));
	}
}
