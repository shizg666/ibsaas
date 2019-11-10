package com.landleaf.ibsaas.common.domain.parking.response;


//开闸记录返回DTO
public class OpengaterecordResponseDTO {

    //记录唯一标记
    private String uniqueId;
    //车牌号码
    private String carCode;
    //车主姓名
    private String userName;
    //收费类型编码
    private String chargeTypeCode;
    //收费类型名称
    private String chargeTypeName;

    //通道类型(进/出)
    private String channelType;

    //进出场通道编码
    private String channelCode;
    //进出场通道名称
    private String channelName;

    //进出场时间
    private String opengaterecordTime;

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChargeTypeCode() {
        return chargeTypeCode;
    }

    public void setChargeTypeCode(String chargeTypeCode) {
        this.chargeTypeCode = chargeTypeCode;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
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

    public String getOpengaterecordTime() {
        return opengaterecordTime;
    }

    public void setOpengaterecordTime(String opengaterecordTime) {
        this.opengaterecordTime = opengaterecordTime;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
