package com.landleaf.ibsaas.common.enums.parking;



import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 心跳记录保持类型
 */
public enum HeartBeatStatusEnum implements BaseEnum {
    LOSE(1, "丢失"),
    KEEP(2, "保持");

    public final int type;
    public String name;

    HeartBeatStatusEnum(int type, String name) {
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


    private static Map<Integer, HeartBeatStatusEnum> map = new HashMap<Integer, HeartBeatStatusEnum>();
    ;

    static {
        for (HeartBeatStatusEnum enumObj : HeartBeatStatusEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static HeartBeatStatusEnum getInstByType(int type) {
        return map.get(type);
    }

}
