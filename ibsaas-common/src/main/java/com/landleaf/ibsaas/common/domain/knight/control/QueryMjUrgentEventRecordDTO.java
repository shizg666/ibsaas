package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门禁报警记录分页查询DTO")
public class QueryMjUrgentEventRecordDTO {
    @ApiModelProperty(value = "门名称", name = "门名称", example = "1", dataType = "String", required = false)
    private String doorName;
    @ApiModelProperty(value = "控制器名称", name = "控制器名称", example = "1", dataType = "String", required = false)
    private String stationName;
    @ApiModelProperty(value = "事件类型", name = "事件类型", example = "1", dataType = "String", required = false)
    private String typename;
    @ApiModelProperty(value = "开始时间", name = "开始时间", example = "2012-01-01 11:11:11", dataType = "String", required = false)
    private String start;
    @ApiModelProperty(value = "结束时间", name = "结束时间", example = "2017-05-07 11:11:11", dataType = "String", required = false)
    private String end;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "String",required = true)
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "String",required = true)
    private int pageSize;

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

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
