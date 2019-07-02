package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/7/2 15:32
 * @description:
 */
public enum  BacnetDeviceTypeEnum {

    /**
     * 各设备配置
     */
    NEW_FAN(1, "四效新风"),

    WEATHER_STATION(2, "气象站"),

    HYDRAULIC_MODULE(3, "水力模块"),

    FAN_COIL(4, "风机盘管"),

    WATER_METER(5, "水表"),

    ELECTRIC_METER(6, "电表"),

    SENSOR(7, "多参数传感器"),

    AHU(8, "AHU"),
    ;

    private Integer deviceType;

    private String deviceDescription;

    BacnetDeviceTypeEnum(Integer deviceType, String deviceDescription) {
        this.deviceType = deviceType;
        this.deviceDescription = deviceDescription;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }
}
