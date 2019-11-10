package com.landleaf.ibsaas.common.enums.parking;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPMessage消息类型
 */
public enum MsgTypeEnum implements BaseEnum {
    PARKING(1,"parking","停车业务相关消息"),
    KNIGHT(2,"knight","门禁业务相关消息"),
    ;
    public final int type;
    public String name;
    private String des;

    MsgTypeEnum(int type, String name,String des) {
        this.type = type;
        this.name = name;
        this.des=des;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public int getType() {
        return this.type;
    }


    private static Map<Integer, MsgTypeEnum> map = new HashMap<Integer, MsgTypeEnum>();;

    static {
        for(MsgTypeEnum enumObj : MsgTypeEnum.values()){
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static MsgTypeEnum getInstByType(int type){
        return map.get(type);
    }
}
