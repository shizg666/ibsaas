package com.landleaf.ibsaas.common.domain.knight.depart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "部门")
public class Depart {
    //路径 1-1-
    @ApiModelProperty(value = "1-1-", name = "路径", example = "1-1-", dataType = "String")
    private String path;
    //上级部门名称
    @ApiModelProperty(value = "总部门", name = "上级部门名称", example = "总部门", dataType = "String")
    private String parentName;
    //部门名称
    @ApiModelProperty(value = "财务", name = "部门名称", example = "财务", dataType = "String")
    private String name;
    //部门id
    @ApiModelProperty(value = "2", name = "部门id", example = "2", dataType = "String")
    private Integer departId;
    //上级部门id
    @ApiModelProperty(value = "1", name = "上级部门id", example = "1", dataType = "String")
    private Integer parentId;
    //false说明该部门没有被删除
    @ApiModelProperty(value = "false", name = "false说明该部门没有被删除", example = "status", dataType = "Boolean")
    private Boolean status;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
