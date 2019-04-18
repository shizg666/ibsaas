package com.landleaf.ibsaas.common.domain.parking.request;

//心跳请求参数
public class HeartBeatDTO {
    private String  clientId;
    public String clientInfo;  //大屏控制器说明信息
    public String clientVersion;
    public int interval;//间隔时间秒

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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
