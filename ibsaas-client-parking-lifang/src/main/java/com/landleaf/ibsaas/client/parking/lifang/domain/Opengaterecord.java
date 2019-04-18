package com.landleaf.ibsaas.client.parking.lifang.domain;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 开闸记录
 */
@Table(name = "tc_opengaterecord")
public class Opengaterecord extends BasicEntity {

    /**
     * 标识ID
     */
    @Column(name = "RecordId")
    private Integer recordId;
    /**
     * 通道ID
     */
    @Column(name = "ChannelID")
    private Integer channelId;
    /**
     * 开闸类型
     */
    @Column(name = "GateType")
    private Integer gateType;
    /**
     * 车辆图片路径
     */
    @Column(name = "ImagePath")
    private String imagePath;
    /**
     * 操作员ID
     */
    @Column(name = "OperatorId")
    private Integer operatorId;
    /**
     * 操作时间
     */
    @Column(name = "OperatorDate")
    private Date operatorDate;
    @Column(name = "IsUpload")
    private Boolean upload;
    @Column(name = "data_source")
    private String dataSource;
    @Column(name = "source_id")
    private Integer sourceId;
    @Column(name = "IsSendSAAS")
    private Boolean sendSAAS;

    @Column(name = "TriggerCarcode")
    private String triggerCarcode;

    @Column(name = "TriggerEvent")
    private Date triggerEvent;
    @Column(name = "Reasons")
    private String reasons;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getGateType() {
        return gateType;
    }

    public void setGateType(Integer gateType) {
        this.gateType = gateType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Boolean getSendSAAS() {
        return sendSAAS;
    }

    public void setSendSAAS(Boolean sendSAAS) {
        this.sendSAAS = sendSAAS;
    }

    public String getTriggerCarcode() {
        return triggerCarcode;
    }

    public void setTriggerCarcode(String triggerCarcode) {
        this.triggerCarcode = triggerCarcode;
    }

    public Date getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(Date triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
