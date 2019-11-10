package com.landleaf.ibsaas.common.domain.leo;


import com.landleaf.ibsaas.common.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色
 *
 * @author wyl
 */
@Table(name = "t_base_user_role")
public class UserRole extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 9099254873957730842L;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 角色编码
     */
    private String roleCode;

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
     * 备注
     */
    private String remark;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
