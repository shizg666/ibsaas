package com.landleaf.ibsaas.common.enums.energy;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询维度类型
 */
public enum DimensionTypeEnum implements BaseEnum {
    YEAR(4, "year", "年"),
    MONTH(3, "month", "月"),
    DAY(2, "date", "日"),
    HOUR(1, "time", "时");

    public final int type;
    public String code;
    public String name;

    DimensionTypeEnum(int type, String code, String name) {
        this.type = type;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
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


    private static Map<Integer, DimensionTypeEnum> map = new HashMap<Integer, DimensionTypeEnum>();
    ;

    static {
        for (DimensionTypeEnum enumObj : DimensionTypeEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static DimensionTypeEnum getInstByType(int type) {
        return map.get(type);
    }

}
