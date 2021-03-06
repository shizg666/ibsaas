package com.landleaf.ibsaas.common.domain.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

@ApiModel(description = "人员类型")
public class EmplyTypeDTO {
    @ApiModelProperty(value = "用户类型", dataType = "String")
    private String emplyType;
    @ApiModelProperty(value = "用户类型名称",  dataType = "String")
    private String emplyTypeName;

    public String getEmplyType() {
        return emplyType;
    }

    public void setEmplyType(String emplyType) {
        this.emplyType = emplyType;
    }

    public String getEmplyTypeName() {
        return emplyTypeName;
    }

    public void setEmplyTypeName(String emplyTypeName) {
        this.emplyTypeName = emplyTypeName;
    }
}
