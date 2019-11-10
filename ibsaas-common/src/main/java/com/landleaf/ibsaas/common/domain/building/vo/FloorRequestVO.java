package com.landleaf.ibsaas.common.domain.building.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(value = "楼栋信息对象 ")
public class FloorRequestVO {

    @ApiModelProperty(value="楼层id",required = true)
    private Long id;
    @ApiModelProperty(value="类型")
    private Integer type ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
