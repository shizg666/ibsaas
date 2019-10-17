package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "灯光设备返回对象")
public class LightDeviceResponseVO {
    @ApiModelProperty(value = "设备主键id")
    private Long id;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品品牌")
    private String brand;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "产品类型")
    private String type;
//    @ApiModelProperty(value = "产品类型")
//    private Long typeId;
    @ApiModelProperty(value = "产品接入协议")
    private String protocol;
    @ApiModelProperty(value = "产品接入协议id")
    private String protocolId;
    @ApiModelProperty(value = "设备名称")
    private String name;
    @ApiModelProperty(value = "设备地址")
    private String adress;
    @ApiModelProperty(value = "网关地址地址")
    private String gateway;
    @ApiModelProperty(value = "mac")
    private String mac;
    @ApiModelProperty(value = "通信协议")
    private String protocolDocking;

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getProtocolDocking() {
        return protocolDocking;
    }

    public void setProtocolDocking(String protocolDocking) {
        this.protocolDocking = protocolDocking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
