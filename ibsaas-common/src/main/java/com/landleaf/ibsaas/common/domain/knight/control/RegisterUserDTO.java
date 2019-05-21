package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "注册人员DTO")
public class RegisterUserDTO {
    //ocs人员主键
    @ApiModelProperty(value = "ocs人员主键", example = "355", dataType = "Integer",required = true)
    private Integer employeeId;
    //ocs房门主键
    @ApiModelProperty(value = "ocs房门主键", example = "55", dataType = "Integer",required = true)
    private Integer doorId;
    //权限开始时间
    @ApiModelProperty(value = "权限开始时间",  example = "20100301165600", dataType = "String",required = true)
    private String startTime;
    //权限开始时间
    @ApiModelProperty(value = "权限结束时间",  example = "20100301165600", dataType = "String",required = true)
    private String endTime;
    //是否特权用户（1是0否）
    @ApiModelProperty(value = "是否特权用户（1是0否）",  example = "0", dataType = "Integer",required = true)
    private Integer isSpecial;


    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }
}
