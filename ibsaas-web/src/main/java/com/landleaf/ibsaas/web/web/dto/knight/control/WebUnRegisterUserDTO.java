package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "解除人员权限DTO")
public class WebUnRegisterUserDTO {
    //ocs人员主键
    @ApiModelProperty(value = "ocs人员主键",  example = "355", dataType = "String", required = true)
    private String employeeId;
    //ocs房门主键
    @ApiModelProperty(value = "ocs房门主键",  example = "55", dataType = "String", required = true)
    private String doorId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }
}
