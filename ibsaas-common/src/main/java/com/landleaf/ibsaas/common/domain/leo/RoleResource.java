package com.landleaf.ibsaas.common.domain.leo;


import com.landleaf.ibsaas.common.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 角色权限
 * @author wyl
 */
@Table(name = "t_base_role_resource")
public class RoleResource extends BasicEntity {

    private static final long serialVersionUID = -2939970656677579763L;
    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限编码
     */
    private String resourceCode;

    /**
     * 所属系统
     */
    private String belongSystem;

    /**
     * 是否可用
     */
    @Column(name = "is_active")
    private Integer active;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 备注
     */
    private String remark;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
