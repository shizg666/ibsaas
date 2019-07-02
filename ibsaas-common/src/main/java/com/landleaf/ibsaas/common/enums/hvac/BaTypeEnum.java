package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/7/2 11:33
 * @description:
 */
public enum  BaTypeEnum {

    /**
     *  BA Bacnet类型
     */
    BA_GATEWAY(0, "BA网关"),
    BA_NETWORK_CONTROLLER(1, "BA网络控制器"),
    ;

    private Integer type;

    private String description;

    BaTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
