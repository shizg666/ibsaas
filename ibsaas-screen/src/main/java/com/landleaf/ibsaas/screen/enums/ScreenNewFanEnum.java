package com.landleaf.ibsaas.screen.enums;

/**
 * @author Lokiy
 * @date 2019/12/11 11:29
 * @description:
 */
public enum  ScreenNewFanEnum {

    /**
     * 机组编号
     */
    UNIT_1("1", "240866071672918016"),

    UNIT_2("2", "240866072289480704"),

    UNIT_3("3", "240866072927014912"),

    UNIT_4("4", "240866073551966208"),

    ;
    private String unitNum;

    private String nodeId;

    ScreenNewFanEnum(String unitNum, String nodeId) {
        this.unitNum = unitNum;
        this.nodeId = nodeId;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
