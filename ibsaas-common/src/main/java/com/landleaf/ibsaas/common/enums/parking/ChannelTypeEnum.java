package com.landleaf.ibsaas.common.enums.parking;



import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 进出类型
 */
public enum ChannelTypeEnum implements BaseEnum {
    //进出类型(0进 1出)
    IN(0, "进"),
    OUT(1, "出");

    public final int type;
    public String name;

    ChannelTypeEnum(int type, String name) {
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


    private static Map<Integer, ChannelTypeEnum> map = new HashMap<Integer, ChannelTypeEnum>();
    ;

    static {
        for (ChannelTypeEnum enumObj : ChannelTypeEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static ChannelTypeEnum getInstByType(int type) {
        return map.get(type);
    }

}
