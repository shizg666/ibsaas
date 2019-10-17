package com.landleaf.ibsaas.client.parking.lifang.mq.domain;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 进出通道
 */
@Table(name = "tc_channel")
public class Channel extends BasicEntity {
    /**
     * 通道ID
     */
    @Column(name = "ChannelID")
    private Integer channelId;
    /**
     * 通道名称
     */
    @Column(name = "ChannelName")
    private String channelName;
    /**
     * 通道类型（0主 1辅）
     */
    @Column(name = "ChannelType")
    private Integer channelType;
    /**
     * 管理终端编号
     */
    @Column(name = "MStationNo")
    private Integer mStationNo;
    /**
     * 进出类型(0进 1出)
     */
    @Column(name = "InOrOut")
    private Integer inOrOut;
    /**
     * 是否判断重复进出
     */
    @Column(name = "IsRepeatInOut")
    private Boolean repeatInOut;
    /**
     * 是否通道管制
     */
    @Column(name = "IsChannelControl")
    private Boolean channelControl;

    @Column(name = "ParkingChannelID")
    private Integer parkingChannelId;

    @Column(name = "ParkingLotID")
    private Integer parkingLotId;

    @Column(name = "ChargeRuleID")
    private Integer chargeRuleId;

    @Column(name = "CameraID")
    private Integer cameraId;
    @Column(name = "CameraID2")
    private Integer cameraId2;
    /**
     * 是否删除
     */
    @Column(name = "IsDelete")
    private Boolean delete;
    /**
     * 删除人
     */
    @Column(name = "DeletePeople")
    private Integer deletePeople;
    @Column(name = "DeleteDate")
    private Date deleteDate;

    @Column(name = "TempTriggerMode")
    private Integer tempTriggerMode;
    @Column(name = "TriggerMode")
    private Integer triggerMode;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "GUID")
    private String guid;


    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getmStationNo() {
        return mStationNo;
    }

    public void setmStationNo(Integer mStationNo) {
        this.mStationNo = mStationNo;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Boolean getRepeatInOut() {
        return repeatInOut;
    }

    public void setRepeatInOut(Boolean repeatInOut) {
        this.repeatInOut = repeatInOut;
    }

    public Boolean getChannelControl() {
        return channelControl;
    }

    public void setChannelControl(Boolean channelControl) {
        this.channelControl = channelControl;
    }

    public Integer getParkingChannelId() {
        return parkingChannelId;
    }

    public void setParkingChannelId(Integer parkingChannelId) {
        this.parkingChannelId = parkingChannelId;
    }

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Integer getCameraId2() {
        return cameraId2;
    }

    public void setCameraId2(Integer cameraId2) {
        this.cameraId2 = cameraId2;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Integer getDeletePeople() {
        return deletePeople;
    }

    public void setDeletePeople(Integer deletePeople) {
        this.deletePeople = deletePeople;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Integer getTempTriggerMode() {
        return tempTriggerMode;
    }

    public void setTempTriggerMode(Integer tempTriggerMode) {
        this.tempTriggerMode = tempTriggerMode;
    }

    public Integer getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(Integer triggerMode) {
        this.triggerMode = triggerMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
