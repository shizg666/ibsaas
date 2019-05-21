package com.landleaf.ibsaas.common.domain.knight.control;

import com.landleaf.ibsaas.common.utils.date.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 注册人员（门禁权限）
 */
@Table(name = "mj_reguser")
@ApiModel(value = "注册人员")
public class MjReguser implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = true, dataType = "String", example = "1")
    private Integer id;
    /**
     * 人员编号
     */
    @ApiModelProperty(value = "人员ID", required = true, dataType = "Integer", example = "人员编号sysNo")
    @Column(name = "sys_no")
    private Integer sysNo;
    /**
     * 门ID
     */
    @ApiModelProperty(value = "门ID", required = true, dataType = "Integer", example = "门ID")
    @Column(name = "door_id")
    private Integer doorId;
    /**
     * 门禁控制器ID
     */
    @ApiModelProperty(value = "门禁控制器ID", required = true, dataType = "Integer", example = "门禁控制器ID")
    @Column(name = "device_id")
    private Integer deviceId;
    /**
     * 组
     */
    @ApiModelProperty(value = "组", required = false, dataType = "Integer", example = "组")
    @Column(name = "group_id")
    private Integer groupId;
    /**
     * 是否特权用户
     */
    @ApiModelProperty(value = "是否特权用户", required = true, dataType = "Integer", example = "是否特权用户（1：是）")
    @Column(name = "is_special")
    private Integer special;

    /**
     * 权限开始时间
     */
    @ApiModelProperty(value = "生效时间", required = true, dataType = "Date", example = "生效时间")
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 权限截止时间
     */
    @ApiModelProperty(value = "失效时间", required = true, dataType = "Date", example = "失效时间")
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "0正常，1挂失", required = true, dataType = "Integer", example = "0")
    @Column(name = "operation_type")
    private Integer operationType;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Integer", example = "0")
    @Column(name = "download_status")
    private Integer downloadStatus;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "String", example = "0")
    @Column(name = "download_status_display")
    private String downloadStatusDisplay;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Date", example = "0")
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "0 门禁设备 1 人脸设备", required = false, dataType = "Integer", example = "0")
    @Column(name = "dev_type")
    private Integer devType;
    /**
     * 下载人脸状态 0 成功，其它失败
     */
    @ApiModelProperty(value = "下载人脸状态 0 成功，其它失败", required = false, dataType = "Integer", example = "0")
    @Column(name = "face_down_status")
    private Integer faceDownStatus;
    /**
     *
     */
    @ApiModelProperty(value = "", required = false, dataType = "Integer")
    @Column(name = "finger_print_down_status")
    private Integer fingerPrintDownStatus;

    /**
     * 权限开始时间
     */
    @Transient
    private String startTimeStr;
    @Transient
    private String endTimeStr;


    public String getStartTimeStr() {
        if(startTime!=null){
            return DateUtils.convert(startTime);
        }
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        if(endTime!=null){
            return DateUtils.convert(endTime);
        }
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

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
}
