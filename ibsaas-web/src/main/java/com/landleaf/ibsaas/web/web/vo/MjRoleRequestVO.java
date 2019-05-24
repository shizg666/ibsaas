package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@ApiModel(description = "角色权限请求信息")
public class MjRoleRequestVO {
    @ApiModelProperty(value = "角色id主键(修改必传)")
    private String id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "部门ID", required = true, dataType = "Integer", example = "0")
    private Integer departId;
//    @ApiModelProperty(value = "部门ID")
//    private String departName;
    @ApiModelProperty(value = "描述", dataType = "String")
    private String descr;
    @ApiModelProperty(value = "门禁id列表")
    private List<Integer> list;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
