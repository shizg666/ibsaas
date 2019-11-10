package com.landleaf.ibsaas.common.domain.knight.attendance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询考勤核算DTO")
public class QueryAttendanceResultDTO {
    @ApiModelProperty(value = "开始时间", name = "开始时间", example = "2017:01:01 00:00:00", dataType = "String")
    private String BgnDate;
    @ApiModelProperty(value = "结束时间", name = "结束时间", example = "2017:01:01 00:00:00", dataType = "String")
    private String EndDate;
    @ApiModelProperty(value = "0：根据人员查询;1：不根据人员查询", name = "", example = "0：根据人员查询;1：不根据人员查询", dataType = "Integer")
    private Integer IgnoreSysNo;
    @ApiModelProperty(value = "0：根据部门查询;1：不根据部门查询", name = "", example = "0：根据部门查询;1：不根据部门查询", dataType = "Integer")
    private Integer IgnoreDptId;
    @ApiModelProperty(value = "部门编号(IgnoreDptId为0时必填)", name = "", example = "部门编号(IgnoreDptId为0时必填)", dataType = "Integer")
    private Integer dptId;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "int")
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "int")
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

    public Integer getIgnoreSysNo() {
        return IgnoreSysNo;
    }

    public void setIgnoreSysNo(Integer ignoreSysNo) {
        IgnoreSysNo = ignoreSysNo;
    }

    public Integer getIgnoreDptId() {
        return IgnoreDptId;
    }

    public void setIgnoreDptId(Integer ignoreDptId) {
        IgnoreDptId = ignoreDptId;
    }

    public Integer getDptId() {
        return dptId;
    }

    public void setDptId(Integer dptId) {
        this.dptId = dptId;
    }
}
