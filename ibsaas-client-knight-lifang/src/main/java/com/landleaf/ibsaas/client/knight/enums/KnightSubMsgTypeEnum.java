package com.landleaf.ibsaas.client.knight.enums;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.leo.User;

import java.util.Map;

/**
 * 门禁业务类型
 */
public enum KnightSubMsgTypeEnum {
    knightExampleMsgProcess(1, "example", "knightExampleMsgProcess", "handle",2, User.class),;

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

    KnightSubMsgTypeEnum(int type, String name, String beanName, String methodName,int paramType,Class paramName) {
        this.type = type;
        this.name = name;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramType=paramType;
        this.paramName=paramName;
    }

    public static Map<String, KnightSubMsgTypeEnum> map = Maps.newHashMap();

    static {
        for (KnightSubMsgTypeEnum knightSubMsgTypeEnum : KnightSubMsgTypeEnum.values()) {
            map.put(knightSubMsgTypeEnum.name,knightSubMsgTypeEnum);
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
