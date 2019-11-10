package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门禁报警记录")
public class MjUrgentEventRecord {
    @ApiModelProperty(value = "1", name = "类型", example = "1", dataType = "Integer")
    private Integer urgentType;
    @ApiModelProperty(value = "1", name = "门状态", example = "1", dataType = "Integer")
    private Integer doorStatus;
    @ApiModelProperty(value = "1", name = "门名称", example = "1", dataType = "String")
    private String doorName;
    @ApiModelProperty(value = "1", name = "门ID", example = "1", dataType = "Integer")
    private Integer doorId;
    @ApiModelProperty(value = "1", name = "设备名称", example = "1", dataType = "String")
    private String stationName;
    @ApiModelProperty(value = "2018-01-12 17:54:53", name = "刷卡时间", example = "2018-01-12 17:54:53", dataType = "String")
    private String eventDate;
    @ApiModelProperty(value = "1", name = "类型名称", example = "1", dataType = "String")
    private String typename;

    public Integer getUrgentType() {
        return urgentType;
    }

    public void setUrgentType(Integer urgentType) {
        this.urgentType = urgentType;
    }

    public Integer getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(Integer doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
