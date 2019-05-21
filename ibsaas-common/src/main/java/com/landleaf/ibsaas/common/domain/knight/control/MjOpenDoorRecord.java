package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门禁记录")
public class MjOpenDoorRecord {
    @ApiModelProperty(value = "羊坊店", name = "门区名称", example = "羊坊店", dataType = "String")
    private String areaName;
    @ApiModelProperty(value = "1", name = "刷卡类型", example = "1", dataType = "Integer")
    private Integer cardType;
    @ApiModelProperty(value = "1", name = "设备ID", example = "1", dataType = "Integer")
    private Integer deviceId;
    @ApiModelProperty(value = "1", name = "门ID", example = "1", dataType = "Integer")
    private Integer doorId;
    @ApiModelProperty(value = "房门", name = "门", example = "房门", dataType = "String")
    private String doorName;
    @ApiModelProperty(value = "1", name = "门状态", example = "1", dataType = "Integer")
    private Integer doorStatus;
    @ApiModelProperty(value = "1", name = "刷卡人员证件号", example = "1", dataType = "String")
    private String employeeId;
    @ApiModelProperty(value = "1", name = "刷卡人员姓名", example = "1", dataType = "String")
    private String employeeName;
    @ApiModelProperty(value = "1", name = "刷卡人员编号", example = "1", dataType = "Integer")
    private Integer employeeSysNo;
    @ApiModelProperty(value = "2018-01-12 17:54:53", name = "刷卡时间", example = "2018-01-12 17:54:53", dataType = "String")
    private String eventDate;
    @ApiModelProperty(value = "1", name = "记录ID", example = "1", dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "1", name = "刷卡人员部门名称", example = "1", dataType = "String")
    private String name;
    @ApiModelProperty(value = "1", name = "刷卡卡号", example = "1", dataType = "Integer")
    private String serial;
    @ApiModelProperty(value = "1", name = "设备名称", example = "1", dataType = "String")
    private String stationName;
    @ApiModelProperty(value = "成员卡", name = "卡描述", example = "1", dataType = "String")
    private String typedescribe;
    @ApiModelProperty(value = "1", name = "出开门", example = "1", dataType = "String")
    private String typename;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
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

    public Integer getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(Integer doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeSysNo() {
        return employeeSysNo;
    }

    public void setEmployeeSysNo(Integer employeeSysNo) {
        this.employeeSysNo = employeeSysNo;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTypedescribe() {
        return typedescribe;
    }

    public void setTypedescribe(String typedescribe) {
        this.typedescribe = typedescribe;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
