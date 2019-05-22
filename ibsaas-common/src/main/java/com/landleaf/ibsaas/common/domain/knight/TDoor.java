package com.landleaf.ibsaas.common.domain.knight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "门信息")
public class TDoor {
    @ApiModelProperty(value="楼栋id（修改必传）")
    private Long id;
    @ApiModelProperty(value="楼层名称",required = true)
    private String name;
    @ApiModelProperty(value="门禁id")
    private Integer controlId;
    @ApiModelProperty(value="楼层id",required = true)
    private Integer parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}