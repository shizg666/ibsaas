package com.landleaf.ibsaas.common.domain.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "人员")
public class Emply {
    @ApiModelProperty(value = "001835", name = "人员系统编号", example = "业务逻辑使用的人员主键", dataType = "Integer")
    private Integer sysNo;
    @ApiModelProperty(value = "1", name = "部门Id", example = "1", dataType = "Integer")
    private Integer departId;
    @ApiModelProperty(value = "部门名称", name = "部门名称", example = "1", dataType = "String")
    private String departName;
    @ApiModelProperty(value = "1832", name = "人员Id", example = "1832", dataType = "String")
    private String employeeId;
    @ApiModelProperty(value = "张君峰", name = "人员名称", example = "张君峰", dataType = "String")
    private String employeeName;
    @ApiModelProperty(value = "卡面编号", name = "卡面编号", example = "卡面编号", dataType = "String")
    private String cardNo;
    @ApiModelProperty(value = "卡号", name = "卡号", example = "卡号", dataType = "String")
    private String serial;
    @ApiModelProperty(value = "人员类型", name = "人员类型", example = "0", dataType = "String")
    private String employeeType;
    @ApiModelProperty(value = "男", name = "人员性别", example = "男", dataType = "Integer")
    private Integer employeeSex;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    private String employeePass;
    @ApiModelProperty(value = "2078-11-17", name = "人员有效期限", example = "2078-11-17", dataType = "Date")
    private Date invalidate;
    @ApiModelProperty(value = "wgj", name = "人员编码", example = "人员编码", dataType = "String")
    private String emplyeeCode;
    @ApiModelProperty(value = "0：未删除;1：删除", name = "删除标记", example = "0：未删除;1：删除", dataType = "Integer")
    private Integer deleted;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    private String emplyeeLoginPass;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    private String cardId;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    private Integer eeSeq;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    private Integer cardType;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    private Integer cardStatus;
    @ApiModelProperty(value = "访客预约登录平台密码", name = "访客预约登录平台密码", example = "", dataType = "String")
    private String password;
    @ApiModelProperty(value = "访问状态，1表示可以访问，0表示不可以访问", name = "访问状态，1表示可以访问，0表示不可以访问", example = "", dataType = "Integer")
    private Integer visitStatus;
    @ApiModelProperty(value = "手机号(keyfree对接用)", name = "手机号(keyfree对接用)", example = "", dataType = "String")
    private String phone;
    @ApiModelProperty(value = "是否离开厂区。0,在厂，1离厂", name = "是否离开厂区。0,在厂，1离厂", example = "", dataType = "Integer")
    private Integer leave;
    @ApiModelProperty(value = "0:普通用户 3 管理员", name = "0:普通用户 3 管理员", example = "", dataType = "Integer")
    private Integer admin;
    @ApiModelProperty(value = "普通用户/管理员", name = "普通用户/管理员", example = "", dataType = "Integer")
    private String adminStr;
    @ApiModelProperty(value = "人员有效期限", name = "人员有效期限", example = "2078-11-17", dataType = "Date")
    private String invalidateStr;

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public Integer getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(Integer employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmployeePass() {
        return employeePass;
    }

    public void setEmployeePass(String employeePass) {
        this.employeePass = employeePass;
    }

    public Date getInvalidate() {
        return invalidate;
    }

    public void setInvalidate(Date invalidate) {
        this.invalidate = invalidate;
    }

    public String getEmplyeeCode() {
        return emplyeeCode;
    }

    public void setEmplyeeCode(String emplyeeCode) {
        this.emplyeeCode = emplyeeCode;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getEmplyeeLoginPass() {
        return emplyeeLoginPass;
    }

    public void setEmplyeeLoginPass(String emplyeeLoginPass) {
        this.emplyeeLoginPass = emplyeeLoginPass;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getEeSeq() {
        return eeSeq;
    }

    public void setEeSeq(Integer eeSeq) {
        this.eeSeq = eeSeq;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getAdminStr() {
        return adminStr;
    }

    public void setAdminStr(String adminStr) {
        this.adminStr = adminStr;
    }

    public String getInvalidateStr() {
        return invalidateStr;
    }

    public void setInvalidateStr(String invalidateStr) {
        this.invalidateStr = invalidateStr;
    }
}
