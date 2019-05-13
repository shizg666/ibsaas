package com.landleaf.ibsaas.common.enums.parking;


import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPMessage子消息类型
 */
public enum SubMsgTypeEnum implements BaseEnum {
    INIT_LINK(1,1,"init_link","初始连接确认"),
    CHANNEL_LIST(2,1,"channel_list","通道类型列表"),
    CHARGERULE_LIST(3,1,"chargerule_list","收费类型列表"),
    PARKING_REAL_COUNT(4,1,"parking_real_count","车位实时数量"),
    PARKING_RECORD(5,1,"parking_record","进出记录列表"),
    CAR_LIST(6,1,"car_list","车辆列表"),
    CAR_DETAIL(7,1,"car_detail","车辆详情"),
    PARKING_REAL_COUNT_HOUR(8,1,"parking_real_count_hour","车位当日各时段数量"),
    HEART_BEAT(9,1,"heart_beat","心跳保持"),
    PARKING_IN_HISTROY(10,1,"parking_in_histroy","车流量历史"),
    ;
    public final int type;
    public int pid;
    public String name;
    public String des;

    SubMsgTypeEnum(int type, int pid,String name,String des) {
        this.type = type;
        this.pid=pid;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    private static Map<Integer, SubMsgTypeEnum> map = new HashMap<Integer, SubMsgTypeEnum>();;

    static {
        for(SubMsgTypeEnum enumObj : SubMsgTypeEnum.values()){
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static SubMsgTypeEnum getInstByType(int type){
        return map.get(type);
    }
}
