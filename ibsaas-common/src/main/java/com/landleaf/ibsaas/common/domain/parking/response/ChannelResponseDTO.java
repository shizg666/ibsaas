package com.landleaf.ibsaas.common.domain.parking.response;


//通道返回DTO
public class ChannelResponseDTO {

    //记录唯一标记
    private String uniqueId;
    //通道编码
    private String channelCode;
    //通道名称
    private String channelName;
    //通道类型
    private String channelType;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
