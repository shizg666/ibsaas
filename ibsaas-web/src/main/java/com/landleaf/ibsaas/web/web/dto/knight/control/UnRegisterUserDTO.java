package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "解除人员权限DTO")
public class UnRegisterUserDTO {
    //ocs人员主键
    @ApiModelProperty(value = "ocs人员主键",  example = "355", dataType = "Integer", required = true)
    private Integer employeeId;
    //ocs房门主键
    @ApiModelProperty(value = "ocs房门主键",  example = "55", dataType = "Integer", required = true)
    private Integer doorId;

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
}
