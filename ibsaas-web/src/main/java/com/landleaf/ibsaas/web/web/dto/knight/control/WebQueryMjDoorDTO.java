package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询门DTO")
public class WebQueryMjDoorDTO {
    @ApiModelProperty(value = "门名称", name = "门名称", example = "1", dataType = "String", required = false)
    private String doorName;
    @ApiModelProperty(value = "控制器名称", name = "控制器名称", example = "1", dataType = "String", required = false)
    private String stationName;
    @ApiModelProperty(value = "分页查询当前页数", name = "分页查询当前页数", example = "1", dataType = "String",required = true)
    private int page;
    @ApiModelProperty(value = "分页查询每页记录数", name = "分页查询每页记录数", example = "1", dataType = "String",required = true)
    private int limit;
    


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
