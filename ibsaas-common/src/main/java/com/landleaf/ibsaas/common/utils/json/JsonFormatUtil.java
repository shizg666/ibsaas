package com.landleaf.ibsaas.common.utils.json;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonFormatUtil {
	/**
	 * json转map
	 * @param str
	 * @return
	 * @author Tony
	 * @date 2015年11月11日上午11:29:37
	 */
	public static Map<String, Object> getMapFromJsonStr(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, HashMap.class);
    }
	
	public static String getJsonFromMap(Map map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
	
	/**
	 * json to list<map<string, object>>
	 * @param str
	 * @author chriswu
	 * @return
	 */
	public static List<Map<String, Object>> getListFromJsonStr(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, ArrayList.class);
    }
	
	public static String getJsonFromObj(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
	public static boolean isEmpty(JSONObject json,String key){
		if(!json.containsKey(key) || null==json.get(key)) return true;
		return false;
	}
	public static boolean isNotEmpty(JSONObject json, String key){
		if(json.containsKey(key) && null!=json.get(key)) return true;
		return false;
	}
	public static boolean isStringEmpty(JSONObject json,String key){
		if(!json.containsKey(key) || null==json.get(key)
				|| StringUtils.isEmpty(String.valueOf(json.get(key)))
				|| "null".equals(String.valueOf(json.get(key)))) return true;
		return false;
	}
}
