package com.landleaf.ibsaas.common.enums.energy;

/**
 * @author Lokiy
 * @date 2019/6/19 13:44
 * @description:
 */
public enum EnergyTypeEnum {

    /**
     * 能耗类型枚举类
     */
    ENERGY_WATER(1),

    ENERGY_ELECTRIC(2),
    ;


    private Integer energyType;

    EnergyTypeEnum(Integer energyType) {
        this.energyType = energyType;
    }

    public Integer getEnergyType() {
        return energyType;
    }

    public void setEnergyType(Integer energyType) {
        this.energyType = energyType;
    }
}
