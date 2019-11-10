package com.landleaf.ibsaas.client.light.enums;


import com.landleaf.ibsaas.common.constant.RedisConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器信息
 */
public enum HostAdressEnum {
	HOST_3F("3F","192.168.10.173","4196", RedisConstants.LIGHT_DEVICE_3F),
	HOST_4F("4F","192.168.10.170","4196",RedisConstants.LIGHT_DEVICE_4F)
	;

	public String floor;
	public String adress;
	public String port;
	//每个服务器对应的Redis缓存值
	public String key;

	HostAdressEnum(String floor, String adress, String port, String key) {
		this.floor = floor;
		this.adress = adress;
		this.port = port;
		this.key = key;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFloor() {
		return floor;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 * @author justin.li
	 * @date 2016年5月12日
	 */

	private static Map<String, HostAdressEnum> map = null; // adress, enum映射
	private static boolean isInit = false;

	public static HostAdressEnum getInstByAdress(String adress){
		if(adress==null){
			return null;
		}
		if(!isInit){
			synchronized(HostAdressEnum.class){
				if(!isInit){
					map = new HashMap<String, HostAdressEnum>();
					for(HostAdressEnum enu : HostAdressEnum.values()){
						map.put(enu.getAdress(), enu);
					}
				}
				isInit = true;
			}
			
		}
		HostAdressEnum pojoEnum = map.get(adress);
		return pojoEnum;
	}


}
