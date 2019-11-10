package com.landleaf.ibsaas.common.domain.parking.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//车位实时数量请求DTO
@ApiModel(value = "车位监控请求实体")
public class UsercrdtmRealCountQueryDTO {
    @ApiModelProperty(value = "唯一ID", required = false, dataType = "String", example = "111")
    private String id;
    @ApiModelProperty(value = "设备ID", required = false, dataType = "String", example = "111")
    private String clientId;
    //总车位
    @ApiModelProperty(value = "总车位数", required = false, dataType = "int", example = "111")
    private int total;

    //占用车位
    @ApiModelProperty(value = "占用车位", required = false, dataType = "int", example = "111")
    private int occupyCount;

    //重置时间
    @ApiModelProperty(value = "重置时间", required = false, dataType = "String", example = "2019-04-18 00:00:01")
    private String resetTime;

    //剩余车位
    @ApiModelProperty(value = "剩余车位", required = false, dataType = "int", example = "111")
    private int remainCount;

    //备注
    @ApiModelProperty(value = "备注", required = false, dataType = "String", example = "111")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOccupyCount() {
        return occupyCount;
    }

    public void setOccupyCount(int occupyCount) {
        this.occupyCount = occupyCount;
    }

    public String getResetTime() {
        return resetTime;
    }

    public void setResetTime(String resetTime) {
        this.resetTime = resetTime;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
