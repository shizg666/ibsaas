package com.landleaf.ibsaas.web.web.dto.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "解除人员权限DTO")
public class UnRegisterUserByDbDTO {
    @ApiModelProperty(value = "权限主键ID", example = "1", dataType = "Integer", required = true)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
