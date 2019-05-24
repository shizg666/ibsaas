package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "楼层信息对象")
public class TFloorVO {

    @ApiModelProperty(value="楼栋id（修改必传）")
    private Long id;
    @ApiModelProperty(value="楼层名称",required = true)
    private String name;
    @ApiModelProperty(value="楼层数（几楼）",required = true)
    private Integer floor;
    @ApiModelProperty(value="楼栋id",required = true)
    private Long parentId;
    @ApiModelProperty(value="图片")
    private String img;
    @ApiModelProperty(value="标志")
    private String key ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }
}