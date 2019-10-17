package com.landleaf.ibsaas.client.light.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 服务器信息
 */
public enum LightHandleEnum {
	HANDLE_SECNCES_CONTROL("1","灯光场景控制处理器(R1S1!)"),
	HANDLE_SECNCES_MONITOR("2","场景变换监听处理器(R1G2AUTO1!)"),
	HANDLE_SECNCES_PULL("3","手动轮询场景，调整值和错误(R1?")
	;

	public String type;
	public String desc;

	LightHandleEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 * @author justin.li
	 * @date 2016年5月12日
	 */

	private static Map<String, LightHandleEnum> map = null; // type, enum映射
	private static boolean isInit = false;

	public static LightHandleEnum getInstByAdress(String adress){
		if(adress==null){
			return null;
		}
		if(!isInit){
			synchronized(LightHandleEnum.class){
				if(!isInit){
					map = new HashMap<String, LightHandleEnum>();
					for(LightHandleEnum enu : LightHandleEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}
			
		}
		LightHandleEnum pojoEnum = map.get(adress);
		return pojoEnum;
	}


}
