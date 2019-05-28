package com.landleaf.ibsaas.common.enums.hvac;

import com.fasterxml.jackson.annotation.JsonValue;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/5/20 14:14
 * @description: BACnet对象枚举类
 */
public enum BacnetObjectEnum {

    /**
     * 模拟输入
     */
    ANALOG_INPUT( 1, "Analog Input", ObjectType.analogInput),
    /**
     * 模拟输出
     */
    ANALOG_OUTPUT( 2, "Analog Output", ObjectType.analogOutput),
    /**
     * 模拟值
     */
    ANALOG_VALUE( 3, "Analog Value", ObjectType.analogValue),
    /**
     * 数字输入
     */
    BINARY_INPUT( 4, "Binary Input", ObjectType.binaryInput),
    /**
     * 数字输出
     */
    BINARY_OUTPUT( 5, "Binary Output", ObjectType.binaryOutput),
    /**
     * 数字值
     */
    BINARY_VALUE( 6, "Binary Value", ObjectType.binaryValue),
    /**
     * 时序表
     */
    CALENDAR( 7, "Calendar", ObjectType.calendar),
    /**
     * 命令
     */
    COMMAND( 8, "Command", ObjectType.command),
    /**
     * 设备
     */
    DEVICE( 9, "Device", ObjectType.device),
    /**
     * 事件登记
     */
    EVENT_ENROLLMENT( 10, "Event Enrollment", ObjectType.eventEnrollment),
    /**
     * 文件
     */
    FILE( 11, "File", ObjectType.file),
    /**
     * 组
     */
    GROUP( 12, "Group", ObjectType.group),
    /**
     * 环
     */
    LOOP( 13, "Loop", ObjectType.loop),
    /**
     * 多态输入
     */
    MULTI_STATE_INPUT( 14, "Multi-state Input", ObjectType.multiStateInput),
    /**
     * 多态输出
     */
    MULTI_STATE_OUTPUT( 15, "Multi-state Output", ObjectType.multiStateOutput),
    /**
     * 通知类
     */
    NOTIFICATION_CLASS( 16, "Notification Class", ObjectType.notificationClass),
    /**
     * 程序
     */
    PROGRAM( 17, "Program", ObjectType.program),
    /**
     * 时间表
     */
    SCHEDULE( 18, "Schedule", ObjectType.schedule),




//    AVERAGING( "Averaging", ObjectType.averaging),
//
//    MULTI_STATE_VALUE( "Multi-state Value", ObjectType.multiStateValue),
//
//    TREND_LOG( "Trend Log", ObjectType.trendLog),
//
//    LIFE_SAFETY_POINT( "Life Safety Point", ObjectType.lifeSafetyPoint),
//
//    LIFE_SAFETY_ZONE( "Life Safety Zone", ObjectType.lifeSafetyZone),
//
//    ACCUMULATOR( "Accumulator", ObjectType.accumulator),
//
//    PULSE_CONVERTER( "Pulse Converter", ObjectType.pulseConverter),
//
//    EVENT_LOG( "Event Log", ObjectType.eventLog),
//
//    TREND_LOG_MULTIPLE( "Trend Log Multiple", ObjectType.trendLogMultiple),
//
//    LOAD_CONTROL( "Load Control", ObjectType.loadControl),
//
//    STRUCTURED_VIEW( "Structured View", ObjectType.structuredView),
//
//    ACCESS_DOOR( "Access Door", ObjectType.accessDoor),


    ;

    public static Map<String, ObjectType> OBJECT_TYPE_MAP = new ConcurrentHashMap<>();
    static {
        for (BacnetObjectEnum boe: BacnetObjectEnum.values()){
           OBJECT_TYPE_MAP.put(boe.objectTypeStr, boe.objectType);
        }
    }

    /**
     * 对象数
     */
    private Integer objectTypeNum;

    /**
     * 对象字符串
     */
    private String objectTypeStr;

    /**
     * 对象类型
     */
    private ObjectType objectType;



    BacnetObjectEnum(Integer objectTypeNum, String objectTypeStr, ObjectType objectType) {
        this.objectTypeNum = objectTypeNum;
        this.objectTypeStr = objectTypeStr;
        this.objectType = objectType;
    }

    public Integer getObjectTypeNum() {
        return objectTypeNum;
    }

    public void setObjectTypeNum(Integer objectTypeNum) {
        this.objectTypeNum = objectTypeNum;
    }


    @JsonValue
    public String getObjectTypeStr() {
        return objectTypeStr;
    }

    public void setObjectTypeStr(String objectTypeStr) {
        this.objectTypeStr = objectTypeStr;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }


    public static ObjectType getObjectType(String objectTypeStr){
        ObjectType objectType = null;
        for (BacnetObjectEnum boe : values()){
            if(boe.getObjectTypeStr().equals(objectTypeStr)){
                objectType = boe.getObjectType();
            }
        }
        if(objectType == null){
            throw new BusinessException("未能匹配到BACnet对象!");
        }
        return objectType;
    }


    /**
     * 从map中获取ObjectType
     * @param objectTypeStr
     * @return
     */
    public static ObjectType getBacnetObjectType(String objectTypeStr){
        return OBJECT_TYPE_MAP.get(objectTypeStr);
    }


    @Override
    public String toString() {
        return "BacnetObjectEnum{" +
                "objectTypeStr='" + objectTypeStr + '\'' +
                '}';
    }
}
