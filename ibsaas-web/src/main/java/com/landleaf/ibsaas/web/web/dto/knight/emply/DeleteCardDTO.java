package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "删卡DTO")
public class DeleteCardDTO {
    @ApiModelProperty(value = "ocs人员主键",  example = "1", dataType = "Integer", required = true)
    private Integer sysNo;

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

}
