package com.landleaf.ibsaas.screen.enums;

/**
 * @author Lokiy
 * @date 2019/12/11 10:27
 * @description:
 */
public enum  ScreenSensorEnum {

    /**
     * 大屏展示 多参数固定节点id  暂时现写死
     */
    SCREEN_SENSOR_1("1", "255806436867706880"),

    SCREEN_SENSOR_2("2", "255806437954031616"),

    SCREEN_SENSOR_3("3", "255806438583177216"),

    SCREEN_SENSOR_4("4", "255806439422038016"),
    ;

    private String floor;

    private String nodeId;

    ScreenSensorEnum(String floor, String nodeId) {
        this.floor = floor;
        this.nodeId = nodeId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
