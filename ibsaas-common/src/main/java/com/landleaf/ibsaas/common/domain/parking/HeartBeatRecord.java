package com.landleaf.ibsaas.common.domain.parking;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import com.landleaf.ibsaas.common.enums.parking.HeartBeatStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import java.util.Date;

//心跳保持记录
@Table(name = "t_parking_heart_beat_record")
public class HeartBeatRecord extends BasicEntity {

    private String clientId;
    private String clientInfo;
    private String clientVersion;
    private Date startTime;
    private Date endTime;
    //记录状态(1：丢失；2：保持）
    /**
     * {@link HeartBeatStatusEnum}
     */
    private Integer status;
    /**
     * 数据版本号
     */
    @ApiModelProperty(value = "数据版本号", required = true, dataType = "Long", example = "1503891045265")
    private Long versionNo;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
}
