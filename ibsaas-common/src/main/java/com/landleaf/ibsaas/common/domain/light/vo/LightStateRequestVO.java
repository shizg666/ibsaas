package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


@ApiModel(description = "灯光设备VO")
@Data
@ToString
public class LightStateRequestVO {

    @ApiModelProperty(value = "楼层id")
    private Long floorId;

}