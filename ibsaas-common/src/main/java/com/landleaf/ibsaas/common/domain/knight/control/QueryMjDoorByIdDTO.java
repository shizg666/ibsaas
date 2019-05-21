package com.landleaf.ibsaas.common.domain.knight.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "根据ID查询门DTO")
public class QueryMjDoorByIdDTO {
    @ApiModelProperty(value = "1", name = "需要查询的门id", example = "1", dataType = "Integer", required = true)
    private Integer doorId;

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }
}
