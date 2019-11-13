package com.landleaf.ibsaas.client.knight.domain.dto.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门状态查询DTO")
public class QueryDoorStatusDTO {
    @ApiModelProperty(value = "门ID", name = "门ID", example = "1", dataType = "Integer")
    private Integer doorId;

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }
}
