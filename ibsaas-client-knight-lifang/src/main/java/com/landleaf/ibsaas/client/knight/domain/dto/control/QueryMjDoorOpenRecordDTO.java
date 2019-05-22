package com.landleaf.ibsaas.client.knight.domain.dto.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询门禁记录DTO")
public class QueryMjDoorOpenRecordDTO {
    @ApiModelProperty(value = "开始时间", name = "开始时间", example = "2012-01-01 11:11:11", dataType = "String", required = false)
    private String start;
    @ApiModelProperty(value = "结束时间", name = "结束时间", example = "2017-05-07 11:11:11", dataType = "String", required = false)
    private String end;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "String",required = true)
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "String",required = true)
    private int pageSize;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

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
