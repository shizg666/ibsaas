package com.landleaf.ibsaas.common.domain.parking.request;

import com.landleaf.ibsaas.common.enums.parking.UsercrdtmInHistoryQueryTypeEnum;

//车位历史监控(进入量)请求DTO
public class UsercrdtmInHistoryQueryDTO {
    private String clientId;

    /**
     * 类型
     * {@link UsercrdtmInHistoryQueryTypeEnum}
     */
    private Integer type;

    private String startTime;

    private String endTime;


    /********返回参数**********/
    //数量
    private Integer count;
    //当前时间段
    private String current;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
