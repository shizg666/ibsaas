package com.landleaf.ibsaas.common.utils.bean.dto;




import com.landleaf.ibsaas.common.utils.number.NumberUtils;

import java.io.Serializable;

public abstract class BaseObject implements Serializable {
	
	private static final long serialVersionUID = -1245891341026451451L;
	
	public static final String ID = "id";
	
	public static final String CREATETIME="createtime";

	public static final String CREATEUSERID = "createuserid";
	
	public static final String VERSION = "version";

	public static final String UPDATETIME = "updatetime";

	public static final String UPDATEUSERID = "updateuserid";


	/** 主键id*/
    protected Long id;
    /** 版本*/
    protected Integer version;
    /** 创建时间*/
    protected Long createtime;
	/** 创建人*/
	private Long createuserid;
    /** 修改时间*/
    protected Long updatetime;
    /** 修改人*/
    protected Long updateuserid;
    
	public BaseObject() {
	}
	public BaseObject(Long id, Long createtime, Long createuserid) {
		super();
		this.id = id;
		this.createtime = createtime;
		this.createuserid = createuserid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	public Long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}

	public Long getUpdateuserid() {
		return updateuserid;
	}

	public void setUpdateuserid(Long updateuserid) {
		this.updateuserid = updateuserid;
	}

	public Long getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Long createuserid) {
		this.createuserid = createuserid;
	}

	public void init(Long id){
		setId(id);
		if(NumberUtils.isEmpty(createtime)){
			setCreatetime(System.currentTimeMillis());
		}
	}
}
