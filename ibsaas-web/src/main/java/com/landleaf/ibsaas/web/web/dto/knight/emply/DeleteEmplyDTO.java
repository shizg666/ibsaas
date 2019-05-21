package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "删除人员DTO")
public class DeleteEmplyDTO {
    //人员系统编号
    @ApiModelProperty(value = "人员系统编号",  example = "1", dataType = "String", required = true)
    private String sysNo;

    public String getSysNo() {
        return sysNo;
    }

    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }
}
