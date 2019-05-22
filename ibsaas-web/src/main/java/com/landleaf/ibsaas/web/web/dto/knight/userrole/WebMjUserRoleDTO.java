package com.landleaf.ibsaas.web.web.dto.knight.userrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门禁用户角色")
public class WebMjUserRoleDTO {
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = true, dataType = "Integer", example = "0")
    private String mjRoleId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true, dataType = "Integer", example = "0")
    private Integer mjUserId;

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
}
