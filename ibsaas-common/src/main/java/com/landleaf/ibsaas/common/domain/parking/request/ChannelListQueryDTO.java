package com.landleaf.ibsaas.common.domain.parking.request;

//通道查询请求DTO
public class ChannelListQueryDTO {


    /**
     * 通道类型（进/出）
     */
    private String channelType;


    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
