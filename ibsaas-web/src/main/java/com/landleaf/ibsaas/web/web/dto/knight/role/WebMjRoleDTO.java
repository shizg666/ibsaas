package com.landleaf.ibsaas.web.web.dto.knight.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel(description = "门禁角色")
public class WebMjRoleDTO {
    private String id;


    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true, dataType = "String", example = "0")
    private String name;
    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", required = true, dataType = "Integer", example = "0")
    private Integer departId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", required = true, dataType = "String", example = "0")
    private String departName;
    /**
     * 备注
     */
    @ApiModelProperty(value = "描述", required = false, dataType = "String")
    private String descr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
