package com.landleaf.ibsaas.common.domain.light.vo;

import com.landleaf.ibsaas.common.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "查询灯光产品VO")
public class LightProductQueryVO extends BaseDTO implements Serializable {

//    @ApiModelProperty(value = "开始时间", name = "开始时间", example = "2012-01-01 11:11:11", dataType = "String", required = false)
//    private String start;
//    @ApiModelProperty(value = "结束时间", name = "结束时间", example = "2017-05-07 11:11:11", dataType = "String", required = false)
//    private String end;

    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "产品品牌")
    private String brand;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "产品类型")
    private String type;
    @ApiModelProperty(value = "产品接入协议")
    private String protocol;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
