package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.beans.Transient;

@ApiModel(description = "门")
public class Door {
    @ApiModelProperty(value = "1", name = "区域ID", example = "1", dataType = "Integer")
    private Integer areaId;
    @ApiModelProperty(value = "0", name = "通道", example = "0", dataType = "Integer")
    private Integer channelnum;
    @ApiModelProperty(value = "1", name = "设备ID", example = "1", dataType = "Integer")
    private Integer devicesysid;
    @ApiModelProperty(value = "121", name = "门ID", example = "121", dataType = "Integer")
    private Integer doorId;
    @ApiModelProperty(value = "1", name = "名称", example = "1", dataType = "String")
    private String doorName;

    private String stationName;
    @ApiModelProperty(value = "门位置名称", name = "门位置名称", example = "1", dataType = "String")
    private String locationName;
    @ApiModelProperty(value = "门位置ID", name = "门位置ID", example = "1", dataType = "String")
    private String locationId;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getChannelnum() {
        return channelnum;
    }

    public void setChannelnum(Integer channelnum) {
        this.channelnum = channelnum;
    }

    public Integer getDevicesysid() {
        return devicesysid;
    }

    public void setDevicesysid(Integer devicesysid) {
        this.devicesysid = devicesysid;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }
}
