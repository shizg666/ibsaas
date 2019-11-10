package com.landleaf.ibsaas.common.enums.parking;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 车流量历史查询类型
 */
public enum UsercrdtmInHistoryQueryTypeEnum implements BaseEnum {
    YEAR(1, "year","年"),
    MONTH(2,"month", "月"),
    DAY(3, "day","日"),
    HOUR(4, "hour","时");

    public final int type;
    public String code;
    public String name;

    UsercrdtmInHistoryQueryTypeEnum(int type,String code, String name) {
        this.type = type;
        this.code=code;
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


    private static Map<Integer, UsercrdtmInHistoryQueryTypeEnum> map = new HashMap<Integer, UsercrdtmInHistoryQueryTypeEnum>();
    ;

    static {
        for (UsercrdtmInHistoryQueryTypeEnum enumObj : UsercrdtmInHistoryQueryTypeEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static UsercrdtmInHistoryQueryTypeEnum getInstByType(int type) {
        return map.get(type);
    }

}
