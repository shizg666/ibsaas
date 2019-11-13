package com.landleaf.ibsaas.client.knight.domain.dto.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel(description = "门禁用户角色")
@Table(name = "t_mj_user_role")
public class MjUserRole {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 角色ID
     */
    @Column(name = "mj_role_id")
    private String mjRoleId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true, dataType = "Integer", example = "0")
    @Column(name = "mj_user_id")
    private Integer mjUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_code")
    private String createUserCode;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @Column(name = "modify_user_code")
    private String modifyUserCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMjRoleId() {
        return mjRoleId;
    }

    public void setMjRoleId(String mjRoleId) {
        this.mjRoleId = mjRoleId;
    }

    public Integer getMjUserId() {
        return mjUserId;
    }

    public void setMjUserId(Integer mjUserId) {
        this.mjUserId = mjUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }
}
