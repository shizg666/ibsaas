package com.landleaf.ibsaas.client.parking.lifang.mq.enums;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 到期状态
 */
public enum ExpireStatusEnum implements BaseEnum {
    UNEXPIRED(1, "未到期"),
    EXPIRED(2, "已过期");

    public final int type;
    public String name;

    ExpireStatusEnum(int type, String name) {
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


    private static Map<Integer, ExpireStatusEnum> map = new HashMap<Integer, ExpireStatusEnum>();
    ;

    static {
        for (ExpireStatusEnum enumObj : ExpireStatusEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static ExpireStatusEnum getInstByType(int type) {
        return map.get(type);
    }

    public static String computedStatus(Date expireDate){

        Date now = new Date();
        if(expireDate.after(now)){
            return ExpireStatusEnum.UNEXPIRED.name;
        }
        return ExpireStatusEnum.EXPIRED.name;
    }
}
