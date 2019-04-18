package com.landleaf.ibsaas.common.utils.obj;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对对象深度clone
 * @author run	
 * 2016年3月7日
 */

public class CopyBeanUtil {

	 private static final Logger LOGGER = LoggerFactory.getLogger(CopyBeanUtil.class);
	    
	 /**
	  * 注意事项: 使用该方法,clone对象 和对象里面的对象必须实现Serializable 接口
	  * clone
	  * @param content
	  * @return
	  * @auther run	
	  * 2016年3月7日
	  */
	public static Object copy(Object content){
		LOGGER.info("==============content复制开始===================");
		try (
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);){
			oos.writeObject(content);
			try(ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					ObjectInputStream ois = new ObjectInputStream(bais);){
				LOGGER.info("==============content复制成功===================");
				return ois.readObject();
			}
		}catch (Exception e) {
			LOGGER.info("==============content复制失败===================");
			LOGGER.error(e.getMessage(),e);
			try{
				LOGGER.info(new GsonBuilder().create().toJson(content));
			}catch(Exception ee){
				
			}
		}
		return null;
	}
}


