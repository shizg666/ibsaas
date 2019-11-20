package com.landleaf.ibsaas.common.domain.knight;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ApiModel(value = "TBuilding楼栋信息对象")
@ToString
public class TBuilding {

    @ApiModelProperty(value="楼栋id（修改毕传）")
    private Long id;
    @ApiModelProperty(value="楼栋名称",required = true)
    private String name;
    @ApiModelProperty(value="类型(1门禁2灯光)")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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