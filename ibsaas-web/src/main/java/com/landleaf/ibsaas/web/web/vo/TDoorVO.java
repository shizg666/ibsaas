package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "门信息")
public class TDoorVO {
    @ApiModelProperty(value="门id主键（修改必传）")
    private Long id;
    @ApiModelProperty(value="门位置名称",required = true)
    private String name;
    @ApiModelProperty(value="门禁id")
    private Integer controlId;
    @ApiModelProperty(value="楼层id",required = true)
    private Long parentId;
    @ApiModelProperty(value="横坐标",required = true)
    private String xPos;
    @ApiModelProperty(value="纵坐标",required = true)
    private String yPos;
    @ApiModelProperty(value="标志")
    private String key ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getxPos() {
        return xPos;
    }

    public void setxPos(String xPos) {
        this.xPos = xPos;
    }

    public String getyPos() {
        return yPos;
    }

    public void setyPos(String yPos) {
        this.yPos = yPos;
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

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}