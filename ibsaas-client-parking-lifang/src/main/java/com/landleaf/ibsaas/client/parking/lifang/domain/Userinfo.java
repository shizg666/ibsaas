package com.landleaf.ibsaas.client.parking.lifang.domain;


import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tc_userinfo")
public class Userinfo extends BasicEntity {
    //标识ID
    @Column(name = "RecordId")
    private Integer recordId;
    //用户编号
    @Column(name = "UserNo")
    private String userNo;
    //车主名称
    @Column(name = "UserName")
    private String userName;
    @Column(name = "ParkingLot")
    private Integer parkingLot;
    @Column(name = "CarCode")
    private String carCode;
    @Column(name = "CarCode1")
    private String carCode1;
    @Column(name = "CarCode2")
    private String carCode2;
    @Column(name = "CarCode3")
    private String carCode3;
    @Column(name = "CarCode4")
    private String carCode4;
    //收费规则ID
    @Column(name = "ChargeRuleID")
    private Integer chargeRuleID;
    //用户属性（0长期 1临时 2系统 3管理）
    @Column(name = "UserPropertiy")
    private Integer userPropertiy;
    //有效开始时间
    @Column(name = "Bgndt")
    private Date bgndt;
    //有效结束时间
    @Column(name = "Enddt")
    private Date enddt;
    //车辆品牌
    @Column(name = "CarLabel")
    private String carLabel;
    //车辆颜色
    @Column(name = "CarColor")
    private String carColor;
    //车辆类型
    @Column(name = "CarStyleid")
    private Integer carStyleid;
    //联系电话
    @Column(name = "UserTel")
    private String userTel;
    //联系地址
    @Column(name = "UserAddress")
    private String userAddress;
    //备注信息
    @Column(name = "UserMemo")
    private String userMemo;
    @Column(name = "IsWhiteList")
    private Integer whiteList;
    @Column(name = "Balance")
    private BigDecimal balance;
    @Column(name = "IsUpload")
    private Boolean upload;
    @Column(name = "Serial")
    private String serial;
    @Column(name = "data_source")
    private String dataSource;
    @Column(name = "source_id")
    private String sourceId;
    @Column(name = "RuleGroupID")
    private Integer ruleGroupId;
    @Column(name = "IsSendSAAS")
    private Boolean sendSAAS;
    @Column(name = "CardType")
    private String cardType;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "SAASOperatorName")
    private String saasOperatorName;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(Integer parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarCode1() {
        return carCode1;
    }

    public void setCarCode1(String carCode1) {
        this.carCode1 = carCode1;
    }

    public String getCarCode2() {
        return carCode2;
    }

    public void setCarCode2(String carCode2) {
        this.carCode2 = carCode2;
    }

    public String getCarCode3() {
        return carCode3;
    }

    public void setCarCode3(String carCode3) {
        this.carCode3 = carCode3;
    }

    public String getCarCode4() {
        return carCode4;
    }

    public void setCarCode4(String carCode4) {
        this.carCode4 = carCode4;
    }

    public Integer getChargeRuleID() {
        return chargeRuleID;
    }

    public void setChargeRuleID(Integer chargeRuleID) {
        this.chargeRuleID = chargeRuleID;
    }

    public Integer getUserPropertiy() {
        return userPropertiy;
    }

    public void setUserPropertiy(Integer userPropertiy) {
        this.userPropertiy = userPropertiy;
    }

    public Date getBgndt() {
        return bgndt;
    }

    public void setBgndt(Date bgndt) {
        this.bgndt = bgndt;
    }

    public Date getEnddt() {
        return enddt;
    }

    public void setEnddt(Date enddt) {
        this.enddt = enddt;
    }

    public String getCarLabel() {
        return carLabel;
    }

    public void setCarLabel(String carLabel) {
        this.carLabel = carLabel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarStyleid() {
        return carStyleid;
    }

    public void setCarStyleid(Integer carStyleid) {
        this.carStyleid = carStyleid;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public Integer getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(Integer whiteList) {
        this.whiteList = whiteList;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getRuleGroupId() {
        return ruleGroupId;
    }

    public void setRuleGroupId(Integer ruleGroupId) {
        this.ruleGroupId = ruleGroupId;
    }

    public Boolean getSendSAAS() {
        return sendSAAS;
    }

    public void setSendSAAS(Boolean sendSAAS) {
        this.sendSAAS = sendSAAS;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSaasOperatorName() {
        return saasOperatorName;
    }

    public void setSaasOperatorName(String saasOperatorName) {
        this.saasOperatorName = saasOperatorName;
    }
}
