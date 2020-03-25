package com.landleaf.ibsaas.screen.enums;

/**
 * @author Lokiy
 * @date 2019/12/13 17:04
 * @description:
 */
public enum  ScreenEnergyDateTypeEnum {

    /**
     * 存放抄表类型
     */
    DAY("DAY"),

    MONTH("MONTH"),

    YEAR("YEAR"),
    ;

    private String type;

    ScreenEnergyDateTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
