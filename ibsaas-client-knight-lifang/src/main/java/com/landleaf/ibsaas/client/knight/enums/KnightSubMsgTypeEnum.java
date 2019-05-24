package com.landleaf.ibsaas.client.knight.enums;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.knight.domain.dto.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.attendance.QueryAttendanceRecordDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.attendance.QueryAttendanceResultDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.control.*;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.AddDepartDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.QueryDepartDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.*;

import java.util.Map;

/**
 * 门禁业务类型
 */
public enum KnightSubMsgTypeEnum {
    QUERY_DEPART(1, "queryDepart", "knightDepartMsgProcess", "queryDepart", 1, QueryDepartDTO.class),
    ADD_DEPART(2, "addDepart", "knightDepartMsgProcess", "addDepart", 1, AddDepartDTO.class),
    DELETE_DEPART(3, "deleteDepart", "knightDepartMsgProcess", "deleteDepart", 1, DeleteDepartDTO.class),
    GET_ALL_EMPLY_LIST(4, "getAllEmplyList", "knightEmplyMsgProcess", "getAllEmplyList", 1, QueryEmplyDTO.class),
    SELECT_EMPLY(5, "selectEmply", "knightEmplyMsgProcess", "selectEmply", 1, QueryEmplyDTO.class),
    ADD_EMPLY(6, "addEmply", "knightEmplyMsgProcess", "addEmply", 1, AddEmplyDTO.class),
    DELETE_EMPLY(7, "deleteEmply", "knightEmplyMsgProcess", "deleteEmply", 1, DeleteEmplyDTO.class),
    SEND_CARD(8, "sendCard", "knightEmplyMsgProcess", "sendCard", 1, SendCardDTO.class),
    DELETE_CARD(9, "deleteCard", "knightEmplyMsgProcess", "deleteCard", 1, DeleteCardDTO.class),
    UPDATE_EMPLY(11, "updateEmply", "knightEmplyMsgProcess", "updateEmply", 1, UpdateEmplyDTO.class),
    REGISTER_USER(12, "registeruser", "knightControlMsgProcess", "registeruser", 1, RegisterUserDTO.class),
    UN_REGISTER_USER(13, "unregisteruser", "knightControlMsgProcess", "unregisteruser", 1, UnRegisterUserDTO.class),
    GET_MJ_DEVICE_ALL(14, "getMjDeviceAll", "knightControlMsgProcess", "getMjDeviceAll", 1, QueryMjDeviceDTO.class),
    GET_MJ_DEVICE_BY_ID(15, "getMjDeviceById", "knightControlMsgProcess", "getMjDeviceById", 1, QueryMjDeviceDTO.class),
    GET_DOOR_INFO_ALL(16, "getDoorInfoAll", "knightControlMsgProcess", "getDoorInfoAll", 1, QueryMjDoorDTO.class),
    GET_DOOR_INFO_BY_ID(17, "getDoorInfoById", "knightControlMsgProcess", "getDoorInfoById", 1, QueryMjDoorByIdDTO.class),
    MJ_OPEN_DOOR_RECORD(18, "mjOpenDoorRecordByDb", "knightControlMsgProcess", "mjOpenDoorRecordByDb", 1, QueryMjDoorOpenRecordDTO.class),
    MJ_URGENT_EVENT_RECORD(19, "mJUrgentEventRecord", "knightControlMsgProcess", "mJUrgentEventRecord", 1, QueryMjUrgentEventRecordDTO.class),
    GET_ATTENDANCE_RESULT(20, "getAttendanceResult", "knightAttendanceMsgProcess", "getAttendanceResult", 1, QueryAttendanceResultDTO.class),
    GET_ATTENDANCE_RECORD(21, "getAttendanceRecord", "knightAttendanceMsgProcess", "getAttendanceRecord", 1, QueryAttendanceRecordDTO.class),
    SET_ATTENDANCE_RECORD(22, "setAttendanceRecord", "knightAttendanceMsgProcess", "setAttendanceRecord", 1, AddAttendanceRecordDTO.class),
    QUERY_REGISTER_USER_BY_DB(25, "queryRegisteruserByDb", "knightControlMsgProcess", "queryRegisteruserByDb", 1, QueryRegisterUserByDbDTO.class),
    GET_MJ_DEVICE_BY_IDS_DB(26, "getMjDeviceByIdsDb", "knightControlMsgProcess", "getMjDeviceByIdsDb", 1, QueryMjDeviceDTO.class),
    GET_MJ_DEVICE_BY_PAGE_DB(27, "getMjDeviceByPageDb", "knightControlMsgProcess", "getMjDeviceByPageDb", 1, QueryMjDeviceDTO.class),
    QUERY_ALL_EMPLYTYPE(27, "queryAllEmplyType", "knightEmplyMsgProcess", "queryAllEmplyType", 1, EmplyTypeDTO.class),
    ;

    /**
     * 业务类型
     */
    public int type;
    /**
     * 业务名称
     */
    public String name;
    /**
     * 业务类
     */
    public String beanName;
    /**
     * 方法名
     */
    public String methodName;
    /**
     * 参数类型（1：object,2:list）
     */
    private int paramType;
    /**
     * 参数名
     */
    public Class paramName;

    KnightSubMsgTypeEnum(int type, String name, String beanName, String methodName, int paramType, Class paramName) {
        this.type = type;
        this.name = name;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramType = paramType;
        this.paramName = paramName;
    }

    public static Map<String, KnightSubMsgTypeEnum> map = Maps.newHashMap();

    static {
        for (KnightSubMsgTypeEnum knightSubMsgTypeEnum : KnightSubMsgTypeEnum.values()) {
            map.put(knightSubMsgTypeEnum.name, knightSubMsgTypeEnum);
        }
    }

    public static KnightSubMsgTypeEnum getByName(String name) {
        return map.get(name);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    public Class getParamName() {
        return paramName;
    }

    public void setParamName(Class paramName) {
        this.paramName = paramName;
    }
}
