package com.landleaf.ibsaas.client.knight.domain.dto.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "查询设备DTO")
public class QueryMjDeviceDTO {
    @ApiModelProperty(value = "需要查询的设备id", name = "需要查询的设备id", example = "1", dataType = "String")
    private Integer deviceSysId;
    @ApiModelProperty(value = "需要查询的设备id集合", name = "需要查询的设备id集合", example = "1", dataType = "String")
    private List<Integer> deviceSysIds;

    @ApiModelProperty(value = "分页查询当前页数",  example = "1", dataType = "String",required = true)
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", example = "1", dataType = "String",required = true)
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

    public Integer getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Integer deviceSysId) {
        this.deviceSysId = deviceSysId;
    }


    public List<Integer> getDeviceSysIds() {
        return deviceSysIds;
    }

    public void setDeviceSysIds(List<Integer> deviceSysIds) {
        this.deviceSysIds = deviceSysIds;
    }
}
