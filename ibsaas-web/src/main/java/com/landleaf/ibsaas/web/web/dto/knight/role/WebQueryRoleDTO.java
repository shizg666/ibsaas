package com.landleaf.ibsaas.web.web.dto.knight.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询角色DTO")
public class WebQueryRoleDTO {
    @ApiModelProperty(value = "角色名称", name = "角色名称", example = "管理员", dataType = "String", required = false)
    private String name;
    @ApiModelProperty(value = "部门", name = "部门", example = "1", dataType = "Integer", required = false)
    private Integer departId;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "String", required = true)
    private int page;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "String", required = true)
    private int limit;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
