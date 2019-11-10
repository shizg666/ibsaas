package com.landleaf.ibsaas.common.domain.building.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(value = "楼栋信息对象 ")
public class BuildingFloorVO {

    @ApiModelProperty(value="楼栋id",required = true)
    private Long id;
    @ApiModelProperty(value="楼层id列表")
    private List<Long> floorIds ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getFloorIds() {
        return floorIds;
    }

    public void setFloorIds(List<Long> floorIds) {
        this.floorIds = floorIds;
    }
}
