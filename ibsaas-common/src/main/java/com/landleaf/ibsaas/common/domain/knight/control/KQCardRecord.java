package com.landleaf.ibsaas.common.domain.knight.control;

import com.landleaf.ibsaas.common.utils.date.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@ApiModel(description = "打卡原始记录表")
public class KQCardRecord {
    @ApiModelProperty(value = "记录ID", name = "记录ID", example = "1", dataType = "Integer")
    private Integer recordId;
    @ApiModelProperty(value = "人员编号", name = "'人员编号'", example = "1", dataType = "Integer")
    private Integer sysNo;
    @ApiModelProperty(value = "刷卡人员证件号", name = "刷卡人员证件号", example = "1", dataType = "String")
    private String employeeId;
    @ApiModelProperty(value = "刷卡卡号", name = "刷卡卡号", example = "1", dataType = "Integer")
    private String serial;
    @ApiModelProperty(value = "刷卡时间", name = "刷卡时间", example = "2018-01-12 17:54:53", dataType = "Date")
    private Date cardTime;
    @ApiModelProperty(value = "'更新时间'", name = "'更新时间'", example = "2018-01-12 17:54:53", dataType = "Date")
    private Date updateTime;
    @ApiModelProperty(value = "打卡类型 0打卡；1手工增加；2手工修改 默认 0", name = "刷卡类型", example = "1", dataType = "Integer")
    @Column(name = "record_type")
    private Integer recordType;
    @ApiModelProperty(value = "设备ID", name = "设备ID", example = "1", dataType = "Integer")
    private Integer deviceSysId;
    @ApiModelProperty(value = "设备类型", name = "设备类型", example = "1", dataType = "Integer")
    private String deviceType;
    @ApiModelProperty(value = "1", name = "设备名称", example = "1", dataType = "String")
    private String deviceName;
    @ApiModelProperty(value = "门ID", name = "门ID", example = "1", dataType = "Integer")
    private Integer doorId;
    @ApiModelProperty(value = "房门", name = "门", example = "房门", dataType = "String")
    private String doorName;
    @ApiModelProperty(value = "操作员id", name = "操作员id", example = "1", dataType = "Integer")
    private Integer operatorId;
    @ApiModelProperty(value = "'操作员名称'", name = "'操作员名称'", example = "1", dataType = "Integer")
    private String operatorName;
    @ApiModelProperty(value = "1", name = "刷卡人员姓名", example = "1", dataType = "String")
    private String employeeName;
    @ApiModelProperty(value = "1", name = "刷卡人员部门名称", example = "1", dataType = "String")
    private String deptName;
    @ApiModelProperty(value = "刷卡人员部门ID", name = "刷卡人员部门ID", example = "1", dataType = "String")
    private String deptId;

    @Transient
    private String cardTimeStr;

    public String getCardTimeStr() {
        if(cardTime!=null){
            return DateUtils.convert(cardTime);
        }
        return cardTimeStr;
    }

    public void setCardTimeStr(String cardTimeStr) {
        this.cardTimeStr = cardTimeStr;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Date getCardTime() {
        return cardTime;
    }

    public void setCardTime(Date cardTime) {
        this.cardTime = cardTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Integer deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}
