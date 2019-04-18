package com.landleaf.ibsaas.common.domain.parking;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 停车车位数初始值
 *
 * @author wyl
 */
@ApiModel(value = "停车车位数初始值")
@Table(name = "t_parking_real_count_init")
public class ParkingRealCountInit extends BasicEntity {

    //车位设备ID(预留字段)
    private String clientId;
    //总车位
    private Integer total;

    //占用车位
    private Integer occupyCount;

    //重置时间
    private String resetTime;

    //剩余车位
    private Integer remainCount;

    //备注
    private String remark;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOccupyCount() {
        return occupyCount;
    }

    public void setOccupyCount(Integer occupyCount) {
        this.occupyCount = occupyCount;
    }

    public String getResetTime() {
        return resetTime;
    }

    public void setResetTime(String resetTime) {
        this.resetTime = resetTime;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
}
