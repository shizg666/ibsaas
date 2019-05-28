package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/5/28 19:01
 * @description:
 */
public enum  BacnetActiveEnum {
    /**
     * bacnet状态
     */
    ACTIVE(1, "1", "Active"),

    INACTIVE(0, "0", "Inactive")

    ;
    private Integer state;

    private String stateStr;

    private String bacnetState;

    BacnetActiveEnum(Integer state, String stateStr, String bacnetState) {
        this.state = state;
        this.stateStr = stateStr;
        this.bacnetState = bacnetState;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getBacnetState() {
        return bacnetState;
    }

    public void setBacnetState(String bacnetState) {
        this.bacnetState = bacnetState;
    }
}
