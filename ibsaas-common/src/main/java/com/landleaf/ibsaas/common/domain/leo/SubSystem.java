package com.landleaf.ibsaas.common.domain.leo;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 子系统定义
 * @author wyl
 */
@ApiModel(value = "子系统实体")
@Table(name = "t_base_system")
public class SubSystem extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 6968423099935578956L;

    /**
     * 编码
     */
    private String systemCode;

    /**
     * 名称
     */
    private String systemName;

    /**
     * 入口url
     */
    private String entryUrl;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 是否可用
     */
    @Column(name = "is_active")
    private Integer active;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
