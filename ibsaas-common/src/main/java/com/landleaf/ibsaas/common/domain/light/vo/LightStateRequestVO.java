package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@ApiModel(description = "灯光设备VO")
@Data
@ToString
public class LightStateRequestVO {

    @NotBlank(message = "楼层id不能为空")
    @ApiModelProperty(value = "楼层id")
    private Long floorId;
    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(value = "地址")
    private String adress;

}