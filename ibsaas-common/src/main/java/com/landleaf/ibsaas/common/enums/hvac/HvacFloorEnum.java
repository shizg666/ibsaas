package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/6/6 11:07
 * @description: 通用楼层  只做初始化等使用
 */
public enum  HvacFloorEnum {

    /**
     * 通用楼层
     */
    B_2_F ( -2, "B2F"),

    B_1_F ( -1, "B1F"),

    /**
     * 不知楼层的通用楼层
     */
    COMMON_F ( 0, "0F"),

    A_1_F ( 1, "1F"),

    A_2_F ( 2, "2F"),

    A_3_F ( 3, "3F"),

    A_4_F ( 4, "4F"),

    A_5_F ( 5, "5F"),

    ;

    private Integer floor;

    private String floorStr;

    HvacFloorEnum(Integer floor, String floorStr) {
        this.floor = floor;
        this.floorStr = floorStr;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getFloorStr() {
        return floorStr;
    }

    public void setFloorStr(String floorStr) {
        this.floorStr = floorStr;
    }
}
