package com.landleaf.ibsaas.client.parking.lifang.mq.domain;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 收费规则
 */
@Table(name = "tc_chargerule")
public class Chargerule extends BasicEntity {

    /**
     * 收费类型ID
     */
    @Column(name = "ChargeRuleID")
    private Integer chargeRuleId;
    /**
     * 收费类型名称
     */
    @Column(name = "ChargeRuleName")
    private String chargeRuleName;
    /**
     * 用户类型(0长期用户 1临时用户)
     */
    @Column(name = "UserType")
    private Integer userType;
    /**
     * 收费规则编号
     */
    @Column(name = "ChargeNo")
    private Integer chargeNo;
    /**
     * 子收费规则ID
     */
    @Column(name = "ChildRuleID")
    private Integer childRuleId;
    @Column(name = "AllFilesName")
    private String allFilesName;
    @Column(name = "PageName")
    private String pageName;
    /**
     * 收费规则描述
     */
    @Column(name = "ChargeRuleDescribe")
    private String chargeRuleDescribe;
    /**
     * 收费类型名称
     */
    @Column(name = "IsFixed")
    private Boolean fixed;
    /**
     * 是否删除
     */
    @Column(name = "IsDelete")
    private Boolean delete;
    /**
     * 删除人
     */
    @Column(name = "DeletePeople")
    private Integer deletePeople;
    @Column(name = "DeleteDate")
    private Date deleteDate;

    @Column(name = "ParkingType")
    private Integer parkingType;
    @Column(name = "IsUpload")
    private Boolean upload;
    @Column(name = "UserChargeType")
    private Integer userChargeType;
    @Column(name = "IsSendSAAS")
    private Boolean sendSAAS;
    @Column(name = "AreaRules")
    private String areaRules;

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public String getChargeRuleName() {
        return chargeRuleName;
    }

    public void setChargeRuleName(String chargeRuleName) {
        this.chargeRuleName = chargeRuleName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(Integer chargeNo) {
        this.chargeNo = chargeNo;
    }

    public Integer getChildRuleId() {
        return childRuleId;
    }

    public void setChildRuleId(Integer childRuleId) {
        this.childRuleId = childRuleId;
    }

    public String getAllFilesName() {
        return allFilesName;
    }

    public void setAllFilesName(String allFilesName) {
        this.allFilesName = allFilesName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getChargeRuleDescribe() {
        return chargeRuleDescribe;
    }

    public void setChargeRuleDescribe(String chargeRuleDescribe) {
        this.chargeRuleDescribe = chargeRuleDescribe;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Integer getDeletePeople() {
        return deletePeople;
    }

    public void setDeletePeople(Integer deletePeople) {
        this.deletePeople = deletePeople;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Integer getParkingType() {
        return parkingType;
    }

    public void setParkingType(Integer parkingType) {
        this.parkingType = parkingType;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public Integer getUserChargeType() {
        return userChargeType;
    }

    public void setUserChargeType(Integer userChargeType) {
        this.userChargeType = userChargeType;
    }

    public Boolean getSendSAAS() {
        return sendSAAS;
    }

    public void setSendSAAS(Boolean sendSAAS) {
        this.sendSAAS = sendSAAS;
    }

    public String getAreaRules() {
        return areaRules;
    }

    public void setAreaRules(String areaRules) {
        this.areaRules = areaRules;
    }
}
