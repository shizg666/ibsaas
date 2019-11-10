package com.landleaf.ibsaas.common.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 业务类型
 */
public enum BusinessTypeEnum implements BaseEnum {
    BUSINESS_KNIGHT(1, "门禁"),
    BUSINESS_Light(2, "灯控");

    public final int type;
    public String name;

    BusinessTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return this.type;
    }


    private static Map<Integer, BusinessTypeEnum> map = new HashMap<Integer, BusinessTypeEnum>();
    ;

    static {
        for (BusinessTypeEnum enumObj : BusinessTypeEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static BusinessTypeEnum getInstByType(int type) {
        return map.get(type);
    }

}
