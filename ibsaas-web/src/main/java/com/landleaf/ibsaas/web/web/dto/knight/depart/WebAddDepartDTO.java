package com.landleaf.ibsaas.web.web.dto.knight.depart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "增加部门DTO")
public class WebAddDepartDTO {
    //部门名称
    @ApiModelProperty(value = "部门名称", example = "财务", dataType = "String",required = true)
    private String name;
    //上级部门id
    @ApiModelProperty(value = "上级部门ID",  example = "1", dataType = "Integer",required = true)
    private Integer parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
