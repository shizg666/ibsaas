package com.landleaf.ibsaas.client.knight.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@ApiModel(description = "人员")
@Table(name = "pb_emply")
public class Emply {
    @ApiModelProperty(value = "001835", name = "人员系统编号", example = "业务逻辑使用的人员主键", dataType = "Integer")
    @Column(name = "sys_no")
    private Integer sysNo;
    @ApiModelProperty(value = "1", name = "部门Id", example = "1", dataType = "Integer")
    @Column(name = "depart_id")
    private Integer departId;
    @ApiModelProperty(value = "1832", name = "人员Id", example = "1832", dataType = "String")
    @Column(name = "employee_id")
    private String employeeId;
    @ApiModelProperty(value = "张君峰", name = "人员名称", example = "张君峰", dataType = "String")
    @Column(name = "employee_name")
    private String employeeName;
    @ApiModelProperty(value = "卡面编号", name = "卡面编号", example = "卡面编号", dataType = "String")
    @Column(name = "card_no")
    private String cardNo;
    @ApiModelProperty(value = "卡号", name = "卡号", example = "卡号", dataType = "String")
    @Column(name = "serial")
    private String serial;
    @ApiModelProperty(value = "人员类型", name = "人员类型", example = "0", dataType = "String")
    @Column(name = "employee_type")
    private String employeeType;
    @ApiModelProperty(value = "男", name = "人员性别", example = "男", dataType = "Integer")
    @Column(name = "employee_sex")
    private Integer employeeSex;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    @Column(name = "employee_pass")
    private String employeePass;
    @ApiModelProperty(value = "2078-11-17", name = "人员有效期限", example = "2078-11-17", dataType = "Date")
    @Column(name = "invalidate")
    private Date invalidate;
    @ApiModelProperty(value = "wgj", name = "人员编码", example = "人员编码", dataType = "String")
    @Column(name = "emplyee_code")
    private String emplyeeCode;
    @ApiModelProperty(value = "0：未删除;1：删除", name = "删除标记", example = "0：未删除;1：删除", dataType = "Integer")
    @Column(name = "is_delete")
    private Integer deleted;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    @Column(name = "emplyee_login_pass")
    private String emplyeeLoginPass;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "String")
    @Column(name = "card_id")
    private String cardId;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    @Column(name = "ee_seq")
    private Integer eeSeq;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    @Column(name = "card_type")
    private Integer cardType;
    @ApiModelProperty(value = "", name = "", example = "", dataType = "Integer")
    @Column(name = "card_status")
    private Integer cardStatus;
    @ApiModelProperty(value = "访客预约登录平台密码", name = "访客预约登录平台密码", example = "", dataType = "String")
    @Column(name = "password")
    private String password;
    @ApiModelProperty(value = "访问状态，1表示可以访问，0表示不可以访问", name = "访问状态，1表示可以访问，0表示不可以访问", example = "", dataType = "Integer")
    @Column(name = "visit_status")
    private Integer visitStatus;
    @ApiModelProperty(value = "手机号(keyfree对接用)", name = "手机号(keyfree对接用)", example = "", dataType = "String")
    @Column(name = "phone")
    private String phone;
    @ApiModelProperty(value = "是否离开厂区。0,在厂，1离厂", name = "是否离开厂区。0,在厂，1离厂", example = "", dataType = "Integer")
    @Column(name = "is_leave")
    private Integer leave;
    @ApiModelProperty(value = "0:普通用户 3 管理员", name = "0:普通用户 3 管理员", example = "", dataType = "Integer")
    @Column(name = "is_admin")
    private Integer admin;

    @Transient
    private String invalidateStr;

    public String getInvalidateStr() {
        return invalidateStr;
    }

    public void setInvalidateStr(String invalidateStr) {
        this.invalidateStr = invalidateStr;
    }

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
}
