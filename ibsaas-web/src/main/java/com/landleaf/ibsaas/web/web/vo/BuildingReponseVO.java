package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "楼栋对象信息")
public class BuildingReponseVO {
    @ApiModelProperty(value="楼栋id")
    private Long id ;

    @ApiModelProperty(value="标志")
    private String key ;

    @ApiModelProperty(value="楼栋名称")
    private String name;
    @ApiModelProperty(value="楼层列表")
    private List<FloorReponseVO> list;

    private Integer type;

    private String typeName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
