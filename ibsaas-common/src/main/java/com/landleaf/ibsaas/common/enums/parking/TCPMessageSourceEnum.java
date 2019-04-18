package com.landleaf.ibsaas.common.enums.parking;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPMessage消息方
 * 先用枚举后改为扩展字段clientId
 */
public enum TCPMessageSourceEnum implements BaseEnum {
    CLIENT_INNER_CAR_SYSTEM(1,"车辆子系统内网tcp客户端","lgc_client_inner_car_system"),
    CLIENT_OUTER_CLOUD_SYSTEM(2,"云服务tcp客户端","client_outer_cloud_system"),
    SERVER(3,"中转服务端","server");

    public final int type;
    public String name;
    public String clientId;

    TCPMessageSourceEnum(int type, String name,String clientId) {
        this.type = type;
        this.name = name;
        this.clientId=clientId;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public int getType() {
        return this.type;
    }


    private static Map<Integer, TCPMessageSourceEnum> map = new HashMap<Integer, TCPMessageSourceEnum>();;

    static {
        for(TCPMessageSourceEnum enumObj : TCPMessageSourceEnum.values()){
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static TCPMessageSourceEnum getInstByType(int type){
        return map.get(type);
    }
}
