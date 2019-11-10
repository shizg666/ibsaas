package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.List;

@ApiModel(value = "角色对象")
public class RoleReponseVO {
    @ApiModelProperty(value="角色id")
    private String id ;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "部门ID")
    private Integer departId;
    @ApiModelProperty(value = "描述")
    private String descr;

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
}
