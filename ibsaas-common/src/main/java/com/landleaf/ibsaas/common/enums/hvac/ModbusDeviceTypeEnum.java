package com.landleaf.ibsaas.common.enums.hvac;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/7/5 17:27
 * @description:
 */
public enum  ModbusDeviceTypeEnum {
    /**
     * modbus设备
     */
    ELECTRIC_METER(6, "电表", "com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO"),

    SENSOR(7, "多参数传感器", "com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO"),
    ;

    private Integer deviceType;

    private String deviceDescription;

    private String classPath;

    ModbusDeviceTypeEnum(Integer deviceType, String deviceDescription, String classPath) {
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

    public static Map<Integer, ModbusDeviceTypeEnum> MAP = new ConcurrentHashMap<>();
    static {
        for (ModbusDeviceTypeEnum bdte: ModbusDeviceTypeEnum.values()){
            MAP.put(bdte.deviceType, bdte);
        }
    }
    public static ModbusDeviceTypeEnum getModbusDeviceTypeEnum(Integer deviceType){
        return MAP.get(deviceType);
    }
}
