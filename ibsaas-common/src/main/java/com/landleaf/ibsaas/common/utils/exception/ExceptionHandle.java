package com.landleaf.ibsaas.common.utils.exception;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.utils.email.SimpleMailSender;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandle {
	protected static Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static void sendMessage(Exception e, String emailAddress, String projectName) {

		// 临时记录后期放入email中进行邮件或者监控中进行信息提醒操作 by lvxijin
		if (e != null && e.getStackTrace() != null && e.getMessage() != null) {

			JSONArray array = null;
			try {
				array = JSONArray.parseArray(e.getMessage());
			} catch (Exception e1) {
				array.clear();
				array.add("can not serialize the stacktrace data");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectName", projectName);
			map.put("title", e.getMessage());
			map.put("message", array);
			try {
				map.put("address", InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
			}
			map.put("time", format.format(new Date()));

			SimpleMailSender.sendToUser(emailAddress, "运行时异常预警", JSONObject
					.toJSONString(map));

		}
	}
}
