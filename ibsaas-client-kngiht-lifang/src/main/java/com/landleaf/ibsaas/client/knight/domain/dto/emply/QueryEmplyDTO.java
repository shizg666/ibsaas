package com.landleaf.ibsaas.client.knight.domain.dto.emply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询人员DTO")
public class QueryEmplyDTO {
    @ApiModelProperty(value = "人员名称",  example = "人员名称", dataType = "String")
    private String employeeName;
    @ApiModelProperty(value = "001835", name = "人员系统编号", example = "业务逻辑使用的人员主键", dataType = "Integer")
    private Integer sysNo;
    @ApiModelProperty(value = "分页查询当前页数",  example = "1", dataType = "int")
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", example = "1", dataType = "int")
    private int pageSize;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
