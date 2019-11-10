package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel(description = "灯光设备VO")
@Data
public class TLightAreaResponseVO {


    @ApiModelProperty(value = "区域名称")
    private String name;
    @ApiModelProperty(value = "设备主键id")
    private Long deviceId;
    @ApiModelProperty(value = "设备地址")
    private String adress;
    @ApiModelProperty(value = "区域编码")
    private Integer code;
    @ApiModelProperty(value = "属性列表")
    private List<LightProductAttributeVO> list;
    @ApiModelProperty(value = "设备当前的状态值(默认是0)")
    private String state = "0";




}