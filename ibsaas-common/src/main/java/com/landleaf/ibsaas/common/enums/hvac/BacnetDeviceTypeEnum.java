package com.landleaf.ibsaas.common.enums.hvac;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/7/2 15:32
 * @description:
 */
public enum  BacnetDeviceTypeEnum {

    /**
     * 各设备配置
     */
    NEW_FAN(1, "四效新风","com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO"),

    WEATHER_STATION(2, "气象站", "com.landleaf.ibsaas.common.domain.hvac.vo.WeatherStationVO"),

    HYDRAULIC_MODULE(3, "水力模块", "com.landleaf.ibsaas.common.domain.hvac.vo.HydraulicModuleVO"),

    FAN_COIL(4, "风机盘管", "com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO"),

    WATER_METER(5, "水表", "com.landleaf.ibsaas.common.domain.hvac.vo.WaterMeterVO"),

    ELECTRIC_METER(6, "电表", "com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO"),

//    SENSOR(7, "多参数传感器", "com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO"),

    AHU(8, "AHU", "com.landleaf.ibsaas.common.domain.hvac.vo.AhuVO"),


    ACHP_DETAIL( 9, "风冷热泵详参", "com.landleaf.ibsaas.common.domain.hvac.vo.AchpDetailVO"),

    ACHP_PUMP_VALVE( 10, "风冷热泵-水阀水泵", "com.landleaf.ibsaas.common.domain.hvac.vo.AchpPumpValveVO"),

    ACHP_MONITOR( 11, "风冷热泵-监测", "com.landleaf.ibsaas.common.domain.hvac.vo.AchpMonitorVO"),

    RAINWATER_COLLECTION( 12, "雨水收集", "com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO"),

    SUMP(13, "集水坑", "com.landleaf.ibsaas.common.domain.hvac.vo.SumpVO"),

    DOMESTIC_WATER(14, "生活水", "com.landleaf.ibsaas.common.domain.hvac.vo.DomesticWaterVO"),

    EXHAUST_BLOWER(15, "排风机", "com.landleaf.ibsaas.common.domain.hvac.vo.ExhaustBlowerVO"),
    ;

    private Integer deviceType;

    private String deviceDescription;

    private String classPath;

    BacnetDeviceTypeEnum(Integer deviceType, String deviceDescription, String classPath) {
        this.deviceType = deviceType;
        this.deviceDescription = deviceDescription;
        this.classPath = classPath;
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

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public static Map<Integer, BacnetDeviceTypeEnum> MAP = new ConcurrentHashMap<>();
    static {
        for (BacnetDeviceTypeEnum bdte: BacnetDeviceTypeEnum.values()){
            MAP.put(bdte.deviceType, bdte);
        }
    }
    public static BacnetDeviceTypeEnum getBacnetDeviceTypeEnum(Integer deviceType){
        return MAP.get(deviceType);
    }
}
