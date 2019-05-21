package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "楼栋信息")
public class BuildingReponseVO {
    @ApiModelProperty(value="楼栋id")
    private Long id ;
    @ApiModelProperty(value="楼栋名称")
    private String name;
    @ApiModelProperty(value="日志类型")
    private List<FloorReponseVO> list;

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
        this.name = name;
    }

    public List<FloorReponseVO> getList() {
        return list;
    }

    public void setList(List<FloorReponseVO> list) {
        this.list = list;
    }
}
