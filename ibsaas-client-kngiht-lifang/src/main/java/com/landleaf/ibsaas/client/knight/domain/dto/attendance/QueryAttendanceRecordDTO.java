package com.landleaf.ibsaas.client.knight.domain.dto.attendance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "考勤打卡记录分页查询DTO")
public class QueryAttendanceRecordDTO {
    @ApiModelProperty(value = "开始时间", name = "开始时间", example = "2017:01:01 00:00:00", dataType = "String")
    private String BgnDate;
    @ApiModelProperty(value = "结束时间", name = "结束时间", example = "2017:01:01 00:00:00", dataType = "String")
    private String EndDate;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "int")
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "int")
    private int pageSize;

    public String getBgnDate() {
        return BgnDate;
    }

    public void setBgnDate(String bgnDate) {
        BgnDate = bgnDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
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
