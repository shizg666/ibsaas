package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询门DTO")
public class QueryMjDoorDTO {
    @ApiModelProperty(value = "门名称", name = "门名称", example = "1", dataType = "String", required = false)
    private String doorName;
    @ApiModelProperty(value = "控制器名称", name = "控制器名称", example = "1", dataType = "String", required = false)
    private String stationName;
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
