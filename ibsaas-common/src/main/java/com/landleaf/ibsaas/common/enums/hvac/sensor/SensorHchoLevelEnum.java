package com.landleaf.ibsaas.common.enums.hvac.sensor;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * @author Lokiy
 * @date 2019/7/11 16:09
 * @description:
 */
public enum  SensorHchoLevelEnum {
    /**
     * 甲醛等级：
     * 优：0~0.03
     * 良：0.03~0.08
     * 中：0.08~0.3
     * 差：0.3+
     */
    EXCELLENT("0.03", "优"),
    GOOD("0.08", "良"),
    AVERAGE("0.3", "中"),
    POOR( String.valueOf(Integer.MAX_VALUE), "差"),

    ;
    private String key;

    private String level;

    SensorHchoLevelEnum(String key, String level) {
        this.key = key;
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static TreeMap<BigDecimal, SensorHchoLevelEnum> HCHO_LEVEL_MAP = new TreeMap<>();
    static {
        for (SensorHchoLevelEnum she: values()){
            HCHO_LEVEL_MAP.put(new BigDecimal(she.key), she);
        }
    }

    public static String getLevel(String hcho){
        if(StringUtils.isBlank(hcho)){
            return EXCELLENT.level;
        }
        return HCHO_LEVEL_MAP.ceilingEntry(new BigDecimal(hcho)).getValue().level;
    }
}
