package com.landleaf.ibsaas.common.enums.energy;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询类型
 */
public enum QueryTypeEnum implements BaseEnum {
    AREA(1, "分区"),
    TYPE(2, "分项");

    public final int type;
    public String name;

    QueryTypeEnum(int type, String name) {
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


    private static Map<Integer, QueryTypeEnum> map = new HashMap<Integer, QueryTypeEnum>();
    ;

    static {
        for (QueryTypeEnum enumObj : QueryTypeEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static QueryTypeEnum getInstByType(int type) {
        return map.get(type);
    }

}
