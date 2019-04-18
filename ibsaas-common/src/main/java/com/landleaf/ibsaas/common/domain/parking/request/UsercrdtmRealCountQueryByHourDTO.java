package com.landleaf.ibsaas.common.domain.parking.request;

//车位实时数量请求DTO
public class UsercrdtmRealCountQueryByHourDTO {
    private String clientId;
    //总车位
    private int total;

    //占用车位
    private int occupyCount;

    //重置时间
    private String resetTime;

    //剩余车位
    private int remainCount;

    //当前时间段
    private String currentHour;

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

    public String getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(String currentHour) {
        this.currentHour = currentHour;
    }
}
