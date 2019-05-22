package com.landleaf.ibsaas.common.domain.knight;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "楼栋信息")
public class TBuilding {

    @ApiModelProperty(value="楼栋id（修改毕传）")
    private Long id;
    @ApiModelProperty(value="楼栋名称",required = true)
    private String name;

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
}