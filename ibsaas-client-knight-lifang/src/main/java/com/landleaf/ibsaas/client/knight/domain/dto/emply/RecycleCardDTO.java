package com.landleaf.ibsaas.client.knight.domain.dto.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "回收卡DTO")
public class RecycleCardDTO {
    @ApiModelProperty(value = "卡号",  example = "1", dataType = "String", required = true)
    private String Serial;

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }
}
