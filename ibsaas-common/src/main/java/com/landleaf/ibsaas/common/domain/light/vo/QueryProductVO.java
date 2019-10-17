package com.landleaf.ibsaas.common.domain.light.vo;


import com.landleaf.ibsaas.common.domain.ChoiceButton;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ApiModel(description = "产品查询列表初始化对象实体")
@ToString
public class QueryProductVO {

    @ApiModelProperty(value = "品牌列表")
    List<ChoiceButton> brand;
    @ApiModelProperty(value = "型号列表")
    List<ChoiceButton> model;
    @ApiModelProperty(value = "协议列表")
    List<ChoiceButton> protocol;
    @ApiModelProperty(value = "类型列表")
    List<ChoiceButton> type;

    public List<ChoiceButton> getBrand() {
        return brand;
    }

    public void setBrand(List<ChoiceButton> brand) {
        this.brand = brand;
    }

    public List<ChoiceButton> getModel() {
        return model;
    }

    public void setModel(List<ChoiceButton> model) {
        this.model = model;
    }

    public List<ChoiceButton> getProtocol() {
        return protocol;
    }

    public void setProtocol(List<ChoiceButton> protocol) {
        this.protocol = protocol;
    }

    public List<ChoiceButton> getType() {
        return type;
    }

    public void setType(List<ChoiceButton> type) {
        this.type = type;
    }
}
