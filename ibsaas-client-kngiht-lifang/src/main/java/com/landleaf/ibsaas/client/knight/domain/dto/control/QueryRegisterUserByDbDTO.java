package com.landleaf.ibsaas.client.knight.domain.dto.control;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询人员权限DTO")
public class QueryRegisterUserByDbDTO {
    @ApiModelProperty(value = "权限主键ID",  example = "1", dataType = "Integer", required = false)
    private Integer id;
    @ApiModelProperty(value = "用户ID", example = "1", dataType = "Integer", required = false)
    private Integer sysNo;
    @ApiModelProperty(value = "门ID",  example = "1", dataType = "Integer", required = false)
    private Integer doorId;
    @ApiModelProperty(value = "设备ID",  example = "1", dataType = "Integer", required = false)
    private Integer deviceId;

    @ApiModelProperty(value = "分页查询当前页数",  example = "1", dataType = "String",required = true)
    private int curPage;
    @ApiModelProperty(value = "分页查询每页记录数", example = "1", dataType = "String",required = true)
    private int pageSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
