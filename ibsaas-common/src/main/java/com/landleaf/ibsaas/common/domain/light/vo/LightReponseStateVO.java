package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@ApiModel(description = "灯光设备VO")
@Data
@ToString
public class LightReponseStateVO {
    @ApiModelProperty(value = "情景")
    private String scenes;

}