package com.landleaf.ibsaas.web.web.dto.knight.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询人员DTO")
public class WebQueryEmplyDTO {
    @ApiModelProperty(value = "人员名称",  example = "人员名称", dataType = "String")
    private String employeeName;
    @ApiModelProperty(value = "001835", name = "人员系统编号", example = "业务逻辑使用的人员主键", dataType = "Integer")
    private Integer sysNo;
    @ApiModelProperty(value = "分页查询当前页数",  example = "1", dataType = "int")
    private int page;
    @ApiModelProperty(value = "分页查询每页记录数", example = "1", dataType = "int")
    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }
}
