package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModelProperty;

public class LightProductAttributeVO {
    @ApiModelProperty(value = "属性主键id")
    private Long id;
    @ApiModelProperty(value = "属性名称")
    private String name;
    @ApiModelProperty(value = "属性值")
    private String value;
    @ApiModelProperty(value = "属性编码")
    private String code;
    @ApiModelProperty(value = "图片名称")
    private String reserved;
    @ApiModelProperty(value = "产品id")
    private Long productId;


    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

}