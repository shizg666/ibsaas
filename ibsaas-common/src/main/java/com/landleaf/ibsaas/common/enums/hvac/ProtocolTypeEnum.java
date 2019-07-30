package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/7/30 14:51
 * @description: 协议类型
 */
public enum ProtocolTypeEnum {

    /**
     * 协议类型
     */
    BACNET(1),

    MODBUS(2),

    COM(3),

    ;

    private Integer protocolType;

    ProtocolTypeEnum(Integer protocolType) {
        this.protocolType = protocolType;
    }

    public Integer getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(Integer protocolType) {
        this.protocolType = protocolType;
    }
}
