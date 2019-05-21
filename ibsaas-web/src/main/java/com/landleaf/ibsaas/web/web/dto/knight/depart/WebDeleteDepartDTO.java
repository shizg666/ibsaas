package com.landleaf.ibsaas.web.web.dto.knight.depart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "删除部门DTO")
public class WebDeleteDepartDTO {
    //部门id
    @ApiModelProperty(value = "部门ID",  example = "1", dataType = "String",required = true)
    private Integer departId;

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }
}
