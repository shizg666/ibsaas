package com.landleaf.ibsaas.common.domain.leo;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色实体
 * @author wyl
 */
@Table(name = "t_base_role")
@ApiModel(value = "角色信息实体")
public class Role extends BasicEntity implements Serializable {

    private static final long serialVersionUID = -7344130091333929664L;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码", required = true, dataType = "String", example = "SUPER_ADMIN")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = false, dataType = "String", example = "超级管理员")
    private String roleName;

    /**
     * 所属系统
     */
    @ApiModelProperty(value = "所属系统", required = true, dataType = "String", example = "LEO")
    private String belongSystem;

    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用", required = true, dataType = "Integer", example = "0")
    @Column(name = "is_active")
    private Integer active;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = false, dataType = "String")
    @Column(name = "remark")
    private String remark;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
