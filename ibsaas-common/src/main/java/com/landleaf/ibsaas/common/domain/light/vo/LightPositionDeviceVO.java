package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(description = "灯光设备和位置信息返回对象")
@Data
@ToString
public class LightPositionDeviceVO {

    @ApiModelProperty(value = "设备名称")
    private String name;
    @ApiModelProperty(value = "产品品牌")
    private String brand;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "产品类型")
    private String type;
    @ApiModelProperty(value = "横坐标")
    private String xPos;
    @ApiModelProperty(value = "纵坐标")
    private String yPos;
}
