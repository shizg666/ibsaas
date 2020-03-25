package com.landleaf.ibsaas.client.knight.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

@ApiModel(description = "设备")
@Table(name = "pb_station")
public class Station {

    @ApiModelProperty(value = "1", name = "主键ID", example = "1", dataType = "Integer")
    @Column(name = "device_sys_id")
    private Integer deviceSysId;
    @ApiModelProperty(value = "端口", name = "端口", example = "0", dataType = "Integer")
    private Integer port;
    @ApiModelProperty(value = "192.168.30.121", name = "设备IP", example = "192.168.30.121", dataType = "String")
    @Column(name = "station_ip")
    private String stationIp;
    @ApiModelProperty(value = "121", name = "设备编号", example = "121", dataType = "String")
    @Column(name = "station_no")
    private String stationNo;
    @Column(name = "q_station_no")
    private String qStationNo;
    @Column(name = "p_station_no")
    private String pStationNo;
    @Column(name = "station_run")
    private String stationRun;
    @Column(name = "last_online")
    private String lastOnline;
    @ApiModelProperty(value = "设备名称", name = "设备名称", example = "121", dataType = "String")
    @Column(name = "station_name")
    private String stationName;
    @ApiModelProperty(value = "梯控设备 sn码", name = "梯控设备 sn码", example = "121", dataType = "String")
    @Column(name = "serial")
    private String serial;
    @ApiModelProperty(value = "开启紧急模式，1开启，0关闭", name = "开启紧急模式，1开启，0关闭", example = "1", dataType = "String")
    @Column(name = "isUrgent")
    private Integer urgent;
    @ApiModelProperty(value = "访客控制器返回传0不启用1启用", name = "访客控制器返回传0不启用1启用", example = "1", dataType = "String")
    @Column(name = "isback")
    private Integer back;
    @ApiModelProperty(value = "'是否为厂区大门", name = "'是否为厂区大门", example = "1", dataType = "Integer")
    @Column(name = "is_door")
    private Integer door;
    @ApiModelProperty(value = "消费模式。1代表金额消费，2代表补贴消费，3代表先补贴后金额", name = "consumptionTypeId", example = "121", dataType = "String")
    @Column(name = "consumption_type_id")
    private String consumptionTypeId;
    @ApiModelProperty(value = "消费设备区分 1:跨网段设备", name = "消费设备区分 1:跨网段设备", example = "A", dataType = "String")
    @Column(name = "segment")
    private Integer segment;
    @ApiModelProperty(value = "设备类型", name = "设备类型", example = "A", dataType = "String")
    @Column(name = "station_type")
    private String stationType;
    @Column(name = "app_type")
    private String appType;
    @Column(name = "device_type")
    private String deviceType;
    @Column(name = "group_id")
    private Integer groupId;

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

    public String getqStationNo() {
        return qStationNo;
    }

    public void setqStationNo(String qStationNo) {
        this.qStationNo = qStationNo;
    }

    public String getpStationNo() {
        return pStationNo;
    }

    public void setpStationNo(String pStationNo) {
        this.pStationNo = pStationNo;
    }

    public String getStationRun() {
        return stationRun;
    }

    public void setStationRun(String stationRun) {
        this.stationRun = stationRun;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    public Integer getBack() {
        return back;
    }

    public void setBack(Integer back) {
        this.back = back;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public String getConsumptionTypeId() {
        return consumptionTypeId;
    }

    public void setConsumptionTypeId(String consumptionTypeId) {
        this.consumptionTypeId = consumptionTypeId;
    }

    public Integer getSegment() {
        return segment;
    }

    public void setSegment(Integer segment) {
        this.segment = segment;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
