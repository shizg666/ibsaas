package com.landleaf.ibsaas.client.knight.domain.dto.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册人员（门禁权限）
 */
@ApiModel(value = "注册人员")
public class MjReguserResponseDTO implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = true, dataType = "String", example = "1")
    private Integer id;
    /**
     * 人员编号
     */
    @ApiModelProperty(value = "人员ID", required = true, dataType = "Integer", example = "人员编号sysNo")
    private Integer sysNo;
    /**
     * 人员名称
     */
    @ApiModelProperty(value = "人员名称", required = true, dataType = "String", example = "人员名称")
    private String employeeName;
    /**
     * 门ID
     */
    @ApiModelProperty(value = "门ID", required = true, dataType = "Integer", example = "门ID")
    private Integer doorId;
    /**
     * 门名称
     */
    @ApiModelProperty(value = "门名称", required = true, dataType = "String", example = "门名称")
    private String doorName;
    /**
     * 门禁控制器ID
     */
    @ApiModelProperty(value = "门禁控制器ID", required = true, dataType = "Integer", example = "门禁控制器ID")
    private Integer deviceId;
    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", required = true, dataType = "String", example = "设备名称")
    private String stationName;
    /**
     * 组
     */
    @ApiModelProperty(value = "组", required = false, dataType = "Integer", example = "组")
    private Integer groupId;
    /**
     * 是否特权用户
     */
    @ApiModelProperty(value = "是否特权用户", required = true, dataType = "Integer", example = "是否特权用户（1：是）")
    private Integer special;

    /**
     * 权限开始时间
     */
    @ApiModelProperty(value = "生效时间", required = true, dataType = "Date", example = "生效时间")
    private Date startTime;
    /**
     * 权限开始时间
     */
    @ApiModelProperty(value = "生效时间", required = true, dataType = "Long", example = "生效时间")
    private Long startTimestamp;
    /**
     * 权限截止时间
     */
    @ApiModelProperty(value = "失效时间", required = true, dataType = "Date", example = "失效时间")
    private Date endTime;
    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间", required = true, dataType = "Long", example = "失效时间")
    private Long endTimestamp;

    /**
     * 状态
     */
    @ApiModelProperty(value = "0正常，1挂失", required = true, dataType = "Integer", example = "0")
    private Integer operationType;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Integer", example = "0")
    private Integer downloadStatus;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "String", example = "0")
    private String downloadStatusDisplay;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Date", example = "0")
    private Date updateTime;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "0 门禁设备 1 人脸设备", required = false, dataType = "Integer", example = "0")
    private Integer devType;
    /**
     * 下载人脸状态 0 成功，其它失败
     */
    @ApiModelProperty(value = "下载人脸状态 0 成功，其它失败", required = false, dataType = "Integer", example = "0")
    private Integer faceDownStatus;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Integer")
    private Integer fingerPrintDownStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getDownloadStatusDisplay() {
        return downloadStatusDisplay;
    }

    public void setDownloadStatusDisplay(String downloadStatusDisplay) {
        this.downloadStatusDisplay = downloadStatusDisplay;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }

    public Integer getFaceDownStatus() {
        return faceDownStatus;
    }

    public void setFaceDownStatus(Integer faceDownStatus) {
        this.faceDownStatus = faceDownStatus;
    }

    public Integer getFingerPrintDownStatus() {
        return fingerPrintDownStatus;
    }

    public void setFingerPrintDownStatus(Integer fingerPrintDownStatus) {
        this.fingerPrintDownStatus = fingerPrintDownStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

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

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
}
