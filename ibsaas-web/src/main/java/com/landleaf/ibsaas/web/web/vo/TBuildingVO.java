package com.landleaf.ibsaas.web.web.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "楼栋信息对象")
public class TBuildingVO {

    @ApiModelProperty(value="楼栋id（修改毕传）")
    private Long id;
    @ApiModelProperty(value="楼栋名称",required = true)
    private String name;
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
}