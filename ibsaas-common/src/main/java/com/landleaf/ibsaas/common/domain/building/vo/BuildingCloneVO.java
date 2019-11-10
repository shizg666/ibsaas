package com.landleaf.ibsaas.common.domain.building.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.List;


@ApiModel(value = "复制楼栋信息对象")
@ToString
public class BuildingCloneVO {

    @ApiModelProperty(value="复制的类型",required = true)
    private Integer type;
    @ApiModelProperty(value="复制对象列表",required = true)
    private List<BuildingFloorVO> bulidings ;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<BuildingFloorVO> getBulidings() {
        return bulidings;
    }

    public void setBulidings(List<BuildingFloorVO> bulidings) {
        this.bulidings = bulidings;
    }
}
