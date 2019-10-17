package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(description = "产品VO返回对象")
@Data
@ToString
public class ProductReponseVO {

    @ApiModelProperty(value = "产品id主键(修改必传)")
    private Long id;
    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "产品品牌")
    private String brand;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "产品类型id")
    private Long typeId;
    @ApiModelProperty(value = "产品类型")
    private String type;
    @ApiModelProperty(value = "产品接入协议")
    private String protocol;
    @ApiModelProperty(value = "产品对接协议")
    private String protocolDocking;

}
