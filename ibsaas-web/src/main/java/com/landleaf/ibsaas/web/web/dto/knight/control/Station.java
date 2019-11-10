package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "设备")
public class Station {
    @ApiModelProperty(value = "1", name = "主键ID", example = "1", dataType = "Integer")
    private Integer deviceSysId;
    @ApiModelProperty(value = "0", name = "端口", example = "0", dataType = "Integer")
    private Integer port;
    @ApiModelProperty(value = "192.168.30.121", name = "设备IP", example = "192.168.30.121", dataType = "String")
    private String stationIp;
    @ApiModelProperty(value = "121", name = "设备编号", example = "121", dataType = "String")
    private String stationNo;
    @ApiModelProperty(value = "A", name = "设备类型", example = "A", dataType = "String")
    private String stationType;

    public Integer getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Integer deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }
}
