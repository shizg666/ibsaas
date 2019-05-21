package com.landleaf.ibsaas.web.web.dto.knight.depart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询部门DTO")
public class QueryDepartDTO {
    @ApiModelProperty(value = "分页查询当前页数", example = "1", dataType = "int",required = true)
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数",example = "1", dataType = "int",required = true)
    private int pageSize;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
