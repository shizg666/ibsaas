package com.landleaf.ibsaas.common.utils.sms;

import java.util.Random;

public class AuthcodeUtil {
	/**
	 * 获得验证码
	 * @return
	 * @author Jason.Li
	 * @date 2015年11月11日上午11:27:53
	 */
	public static String getAuthcode() {
		Random random = new Random();
		int code = random.nextInt(1000000);
		return String.format("%06d", code);
	}
}
