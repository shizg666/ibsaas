package com.landleaf.ibsaas.web.web.dto.knight.depart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询部门DTO")
public class WebQueryDepartDTO {
    @ApiModelProperty(value = "分页查询当前页数", example = "1", dataType = "int", required = true)
    private int page;
    @ApiModelProperty(value = "分页查询每页记录数", example = "1", dataType = "int", required = true)
    private int limit;

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
