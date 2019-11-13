package com.landleaf.ibsaas.common.domain.parking.request;

//车辆查询请求DTO
public class UserinfoListQueryDTO {

    private Integer page=1;

    private Integer limit=10;

    //到期时间
    private String expireTime;

    //到期状态
    private String expireStatus;

    //收费类型编码
    private String chargeTypeCode;
    //收费类型名称
    private String chargeTypeName;

    //车牌号码
    private String carCode;

    //车主姓名
    private String userName;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(String expireStatus) {
        this.expireStatus = expireStatus;
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

}
