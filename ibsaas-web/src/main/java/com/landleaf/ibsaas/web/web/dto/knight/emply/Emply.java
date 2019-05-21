package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "人员")
public class Emply {
    @ApiModelProperty(value = "1", name = "部门Id", example = "1", dataType = "Integer")
    private Integer dptId;
    @ApiModelProperty(value = "测试部门", name = "部门名称", example = "测试部门", dataType = "String")
    private String dptName;
    @ApiModelProperty(value = "wgj", name = "人员编码", example = "人员编码", dataType = "String")
    private String emplyCode;
    @ApiModelProperty(value = "1832", name = "人员Id", example = "1832", dataType = "String")
    private String emplyId;
    @ApiModelProperty(value = "张君峰", name = "人员名称", example = "张君峰", dataType = "String")
    private String emplyName;
    @ApiModelProperty(value = "男", name = "人员性别", example = "男", dataType = "String")
    private String emplySex;
    @ApiModelProperty(value = "2078-11-17", name = "人员有效期限", example = "2078-11-17", dataType = "String")
    private String invalidate;
    @ApiModelProperty(value = "1", name = "删除标记", example = "0：未删除;1：删除", dataType = "String")
    private String isDeleted;
    @ApiModelProperty(value = "001835", name = "人员系统编号", example = "业务逻辑使用的人员主键", dataType = "String")
    private String sysno;

    public Integer getDptId() {
        return dptId;
    }

    public void setDptId(Integer dptId) {
        this.dptId = dptId;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getEmplyCode() {
        return emplyCode;
    }

    public void setEmplyCode(String emplyCode) {
        this.emplyCode = emplyCode;
    }

    public String getEmplyId() {
        return emplyId;
    }

    public void setEmplyId(String emplyId) {
        this.emplyId = emplyId;
    }

    public String getEmplyName() {
        return emplyName;
    }

    public void setEmplyName(String emplyName) {
        this.emplyName = emplyName;
    }

    public String getEmplySex() {
        return emplySex;
    }

    public void setEmplySex(String emplySex) {
        this.emplySex = emplySex;
    }

    public String getInvalidate() {
        return invalidate;
    }

    public void setInvalidate(String invalidate) {
        this.invalidate = invalidate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSysno() {
        return sysno;
    }

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }
}
