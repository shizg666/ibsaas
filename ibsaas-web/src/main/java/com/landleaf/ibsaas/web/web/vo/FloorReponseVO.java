package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "楼层信息")
public class FloorReponseVO {
    private Long id ;
    @ApiModelProperty(value="标志")
    private String key ;

    @ApiModelProperty(value="楼层名称")
    private String name;
    @ApiModelProperty(value="楼层图片")
    private String img;
    @ApiModelProperty(value="楼栋id")
    private Long parentId;
    @ApiModelProperty(value="门列表")
    private List<RoleDoorsReponseVO> dataList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<RoleDoorsReponseVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<RoleDoorsReponseVO> dataList) {
        this.dataList = dataList;
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
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
