package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询设备DTO")
public class QueryMjDeviceDTO {
    @ApiModelProperty(value = "需要查询的设备id", name = "需要查询的设备id", example = "1", dataType = "String")
    private Integer deviceSysId;

    public Integer getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Integer deviceSysId) {
        this.deviceSysId = deviceSysId;
    }
}
