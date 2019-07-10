package com.landleaf.ibsaas.common.domain.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@ApiModel(description = "灯光产品信息")
public class TLightProduct {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "产品id主键(修改必传)")
    private Long id;
    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "产品品牌")
    private String brand;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "产品类型")
    private String type;
    @ApiModelProperty(value = "产品接入协议")
    private String protocol;
    @ApiModelProperty(value = "产品对接协议")
    private String protocolDocking;
    @ApiModelProperty(value = "产品创建时间")
    private Date ctime;
    @ApiModelProperty(value = "产品修改时间")
    private Date utime;
    private String reserved1;
    private String reserved2;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public String getProtocolDocking() {
        return protocolDocking;
    }

    public void setProtocolDocking(String protocolDocking) {
        this.protocolDocking = protocolDocking == null ? null : protocolDocking.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1 == null ? null : reserved1.trim();
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2 == null ? null : reserved2.trim();
    }
}