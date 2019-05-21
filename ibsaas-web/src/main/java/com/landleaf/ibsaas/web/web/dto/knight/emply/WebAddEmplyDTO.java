package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "添加人员DTO")
public class WebAddEmplyDTO {
    @ApiModelProperty(value = "部门Id", example = "1", dataType = "Integer", required = true)
    private Integer departId;
    @ApiModelProperty(value = "人员姓名", example = "张三", dataType = "String", required = true)
    private String employeeName;
    @ApiModelProperty(value = "用户类型，必填(A-N；大写)",  example = "用户类型，必填(A-N；大写)", dataType = "String", required = true)
    private String employeeType;
    @ApiModelProperty(value = "证件号", example = "0001", dataType = "String", required = false)
    private String employeeId;
    @ApiModelProperty(value = "手机号", example = "18677778888", dataType = "String", required = false)
    private String phone;
    @ApiModelProperty(value = "性别,选填(1 :男，2 :女)",  example = "性别,选填(1 :男，2 :女)", dataType = "String", required = false)
    private String employeeSex;
    @ApiModelProperty(value = "地址",example = "杭州", dataType = "String", required = false)
    private String empAddress;

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }
}
