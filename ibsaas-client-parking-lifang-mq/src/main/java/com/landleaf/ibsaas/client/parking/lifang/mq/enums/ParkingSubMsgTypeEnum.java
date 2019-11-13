package com.landleaf.ibsaas.client.parking.lifang.mq.enums;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.parking.request.*;

import java.util.Map;

/**
 * 停车业务类型
 */
public enum ParkingSubMsgTypeEnum {
    CHANNEL_LIST(1, "CHANNEL_LIST", "parkingChannelListMsgProcess", "queryChannelByType", 1, ChannelListQueryDTO.class),
    CHARGE_RULE_LIST(2, "CHARGE_RULE_LIST", "parkingChargeRuleListMsgProcess", "queryAllChargerule", 1, BaseQueryDTO.class),
    REAL_COUNT_F_HOUR(3, "REAL_COUNT_F_HOUR", "parkingRealCountFhourMsgProcess", "realCountFHour", 1, UsercrdtmRealCountQueryByHourDTO.class),
    REAL_COUNT_HOUR(4, "REAL_COUNT_HOUR", "parkingRealCountMsgProcess", "realCount", 1, UsercrdtmRealCountQueryDTO.class),
    USER_CRDTM_LIST(5, "USER_CRDTM_LIST", "parkingUsercrdtmMsgProcess", "userCrdtmList", 1, UsercrdtmListQueryDTO.class),
    TRAFFIC_FLOW(6, "TRAFFIC_FLOW", "parkingUsercrdtmMsgProcess", "trafficFlow", 1, UsercrdtmInHistoryQueryDTO.class),
    USERI_NFO_LIST(7, "USERI_NFO_LIST", "parkingUserinfoMsgProcess", "userinfoList", 1, UserinfoListQueryDTO.class),
    USERI_NFO(8, "USERI_NFO", "parkingUserinfoMsgProcess", "userinfo", 1, UserinfoDetailQueryDTO.class),
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

    ParkingSubMsgTypeEnum(int type, String name, String beanName, String methodName, int paramType, Class paramName) {
        this.type = type;
        this.name = name;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramType = paramType;
        this.paramName = paramName;
    }

    public static Map<String, ParkingSubMsgTypeEnum> map = Maps.newHashMap();

    static {
        for (ParkingSubMsgTypeEnum knightSubMsgTypeEnum : ParkingSubMsgTypeEnum.values()) {
            map.put(knightSubMsgTypeEnum.name, knightSubMsgTypeEnum);
        }
    }

    public static ParkingSubMsgTypeEnum getByName(String name) {
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
