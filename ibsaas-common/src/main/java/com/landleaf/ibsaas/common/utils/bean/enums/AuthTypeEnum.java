package com.landleaf.ibsaas.common.utils.bean.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 验证码类型
 * @author Jason
 */
public enum AuthTypeEnum implements BaseEnum {
	DRIVERCOURIER_FORGET_PASSWORD(1,"快递员忘记密码"), 
	DRIVERCOURIER_REGISTE(2,"快递员注册"),
	CUSTOMER_LOGIN(3,"客户登录验证码"),
	CUSTOMER_REGISTE(4,"客户注册"),
	CUSTOMER_FORGET_PAY_PASSWORD(5,"客户忘记支付密码"),
	AUTHCODE(6,"验证码"),
	WEB_FORGET_PASSORD(7,"平台忘记密码"),
	PERSONAL_LOGIN(8,"个人客户登录验证码"),
	PERSONAL_REGISTER(9,"个人客户注册"),
	PERSONAL_FORGET_PASSWORD(10,"个人客户忘记密码"),
	CUSTOMER_FORGET_PASSWORD(11,"客户忘记密码"),
	DRIVER_FORGET_PASSWORD(12,"班车司机忘记密码");
	public final int type;
	public String name;

	AuthTypeEnum(int type,String name) {
		this.type = type;
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public int getType() {
		return this.type;
	}
	
	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 * @author justin.li
	 * @date 2016年5月12日
	 */

	private static Map<Integer, AuthTypeEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static AuthTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(AuthTypeEnum.class){
				if(!isInit){
					map = new HashMap<Integer, AuthTypeEnum>();
					for(AuthTypeEnum enu : AuthTypeEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}
			
		}
		AuthTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
