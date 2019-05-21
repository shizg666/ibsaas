package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "查询设备DTO")
public class QueryMjDeviceDTO {
    @ApiModelProperty(value = "需要查询的设备id", name = "需要查询的设备id", example = "1", dataType = "String")
    private Integer deviceSysId;
    @ApiModelProperty(value = "需要查询的设备id集合", name = "需要查询的设备id集合", example = "1", dataType = "String")
    private List<Integer> deviceSysIds;



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
