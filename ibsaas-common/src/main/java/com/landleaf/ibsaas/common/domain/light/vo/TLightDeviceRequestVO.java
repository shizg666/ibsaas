package com.landleaf.ibsaas.common.domain.light.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "灯光设备VO")
public class TLightDeviceRequestVO {

    @ApiModelProperty(value = "设备主键id")
    private Long id;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "设备名称")
    private String name;
    @ApiModelProperty(value = "设备地址")
    private String adress;
    @ApiModelProperty(value = "设备网关地址号")
    private String gateway;
    @ApiModelProperty(value = "设备Mac")
    private String mac;
    @ApiModelProperty(value = "设备通信协议配置")
    private String protocolDocking;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway == null ? null : gateway.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getProtocolDocking() {
        return protocolDocking;
    }

    public void setProtocolDocking(String protocolDocking) {
        this.protocolDocking = protocolDocking == null ? null : protocolDocking.trim();
    }


}