package com.landleaf.ibsaas.common.domain.light.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@ApiModel(description = "产品查询列表初始化对象")
@ToString
public class QueryProductVO {

    @ApiModelProperty(value = "类型列表")
    Set<String> brand;
    @ApiModelProperty(value = "型号列表")
    Set<String> model;
    @ApiModelProperty(value = "协议列表")
    Set<String> protocol;
    @ApiModelProperty(value = "类型列表")
    Map<Long, String> type;

    public Set<String> getBrand() {
        return brand;
    }

    public void setBrand(Set<String> brand) {
        this.brand = brand;
    }

    public Set<String> getModel() {
        return model;
    }

    public void setModel(Set<String> model) {
        this.model = model;
    }

    public Set<String> getProtocol() {
        return protocol;
    }

    public void setProtocol(Set<String> protocol) {
        this.protocol = protocol;
    }

    public Map<Long, String> getType() {
        return type;
    }

    public void setType(Map<Long, String> type) {
        this.type = type;
    }
}
