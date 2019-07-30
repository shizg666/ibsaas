package com.landleaf.ibsaas.common.enums.light;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPMessage子消息类型
 */
public enum LightProcotolEnum  {
    PROTOCOL_TCP("1", "tcp/ip" ),
    PROTOCOL_RS232("2", "re232" )

    ;
    public  String type;

    public String name;


    LightProcotolEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }


    private static Map<String, LightProcotolEnum> map = new HashMap<String, LightProcotolEnum>();;

    static {
        for(LightProcotolEnum enumObj : LightProcotolEnum.values()){
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static LightProcotolEnum getInstByType(String type){
        return map.get(type);
    }
}
