package com.landleaf.ibsaas.client.knight.utils;

import com.google.gson.Gson;

import java.util.Date;

/**单实例方式使用Gson，避免大量占用内存
 * @author 董志勇
 *
 */
public enum MessageUtil {
	
	//单实例
    INSTANCE;
    
    //gson线程安全
    private final static Gson gson = new Gson();
    
    private MessageUtil() 
	{	
    	
	}
    
    public static MessageUtil getInstance()
	{
		return INSTANCE;
	}	
    
	/**将bean转换为json字符串
	 * @param obj
	 * @return
	 */
	public String toJson(Object bean)
	{
		return gson.toJson(bean);
	}
	
	public Gson getGson()
	{
		return gson;
	}
	
	/**命令id =时间戳 + 任意数字串
     * @param size
     * @return
     */
    public static String generateId(int size)
	{
		return  DateUtil.formatJavaDate2(new Date())+"_"+RandomUtil.generateNumberString(size);		
	}
	
}
