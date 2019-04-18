package com.landleaf.ibsaas.common.utils.bean.enums;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO状态枚举工具类
 * @author jason.li
 */
public class EnumUtil {
	
	
	 /**
	  * 根据名字获取类型
	  * @param c
	  * @param name
	  * @return
	  */
	  public static Integer getTypeByName(Class<?> c,String name) {
		 try{
		  BaseEnum[] BaseEnums=(BaseEnum[])c.getMethod("values").invoke(null);
		  for(BaseEnum e:BaseEnums){
			 if(e.getName().equals(name)){
				 return e.getType();
			 }
		  }
		 }catch(Exception e){
			 return null;
		 }
		  return null;
	  }

	
  /**
   * 根据type获得枚举的name
   * @param c  枚举class
   * @param type 类型
   * @return
   * @author jason.li
   * @date 2015年10月28日 上午11:05:40
   */
  public static String getNameByType(Class<?> c,int type) {
	 try{
	  BaseEnum[] BaseEnums=(BaseEnum[])c.getMethod("values").invoke(null);
	  for(BaseEnum e:BaseEnums){
		 if(e.getType()==type){
			 return e.getName();
		 }
	  }
	 }catch(Exception e){
		 return null;
	 }
	  return null;
  }

	/**
	 * 枚举转Map
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author May.ma
	 * @date 2015年10月28日16:07:43
	 */
	public static Map<Integer,Object> getAllToMap(Class<?> clazz) throws Exception {
		Map<Integer, Object> returnMap = new HashMap<>();
		for (Object object : clazz.getEnumConstants()) {
			Method getName = object.getClass().getMethod(BaseEnum.GET_NAME);
			Method getType = object.getClass().getMethod(BaseEnum.GET_TYPE);
			returnMap.put((Integer) getType.invoke(object), getName.invoke(object));
		}
		return returnMap;
	}

	/**
	 * 枚举转List
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author May.ma
	 * @date 2015年10月28日16:07:43
	 */
	public static List<Map<String, Object>> getAllToList(Class<?> clazz) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (Object object : clazz.getEnumConstants()) {
				Map<String, Object> enmuMap = new HashMap<>();
				Method getName = object.getClass().getMethod(BaseEnum.GET_NAME);
				Method getType = object.getClass().getMethod(BaseEnum.GET_TYPE);
				enmuMap.put("type",getType.invoke(object));
				enmuMap.put("name", getName.invoke(object));
				list.add(enmuMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	/**
	 * 枚举转List
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author May.ma
	 * @date 2015年10月28日16:07:43
	 */
	public static List<Map<String, Object>> getAllToListByExclusion(Class<?> clazz,List<Integer> exclusions) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (Object object : clazz.getEnumConstants()) {
				Map<String, Object> enmuMap = new HashMap<>();
				Method getName = object.getClass().getMethod(BaseEnum.GET_NAME);
				Method getType = object.getClass().getMethod(BaseEnum.GET_TYPE);
				Object type=getType.invoke(object);
				if(type instanceof Integer){
					if(exclusions.contains(type)) {
                        continue;
                    }
				}
				enmuMap.put("type",type);
				enmuMap.put("name", getName.invoke(object));
				list.add(enmuMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
