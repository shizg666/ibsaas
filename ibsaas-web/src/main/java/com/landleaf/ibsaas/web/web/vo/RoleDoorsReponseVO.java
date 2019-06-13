package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色门禁信息")
public class RoleDoorsReponseVO {
    private Long id;
    @ApiModelProperty(value="门名称")
    private String name;
    @ApiModelProperty(value="楼层id")
    private Long parentId;
    @ApiModelProperty(value="门禁id")
    private Integer controlId;
    @ApiModelProperty(value="横坐标")
    private String xPos;
    @ApiModelProperty(value="纵坐标")
    private String yPos;
    @ApiModelProperty(value="是否拥有")
    private Boolean acessflag;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
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

    public Boolean getAcessflag() {
        return acessflag;
    }

    public void setAcessflag(Boolean acessflag) {
        this.acessflag = acessflag;
    }
}