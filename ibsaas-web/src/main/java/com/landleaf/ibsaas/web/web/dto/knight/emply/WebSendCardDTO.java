package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "发卡DTO")
public class WebSendCardDTO {
    @ApiModelProperty(value = "ocs人员主键", example = "1", dataType = "Integer", required = true)
    private Integer sysNo;
    @ApiModelProperty(value = "卡号,必填（16位卡号不足补\"0\"）",  example = "00000000ABCDEFGH", dataType = "String", required = true)
    private String serial;

    public Integer getSysNo() {
        return sysNo;
    }
    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
}
