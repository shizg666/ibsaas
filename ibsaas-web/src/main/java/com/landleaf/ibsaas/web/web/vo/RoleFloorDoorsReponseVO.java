package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "角色楼层门禁信息")
public class RoleFloorDoorsReponseVO {
    @ApiModelProperty(value="角色id")
    private String roleId;
    @ApiModelProperty(value="楼层图片锦")
    private String img;
    @ApiModelProperty(value="门禁列表")
    private List<RoleDoorsReponseVO> list;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<RoleDoorsReponseVO> getList() {
        return list;
    }

    public void setList(List<RoleDoorsReponseVO> list) {
        this.list = list;
    }
}