package com.landleaf.ibsaas.common.utils.bean.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 交互使用的BEAN 
 */
public class InterBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String code="0"; // 返回码。0为成功
	private String desc="失败"; // 返回描述
	private Object obj=null; // 返回对象
	private List<Object> objs=null; // 返回列表
	private Map<String, Object> objMap=null; // 返回映射
	

	public void set(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public List<Object> getObjs() {
		return objs;
	}
	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}
	public Map<String, Object> getObjMap() {
		return objMap;
	}
	public void setObjMap(Map<String, Object> objMap) {
		this.objMap = objMap;
	}
	
	
	
}
