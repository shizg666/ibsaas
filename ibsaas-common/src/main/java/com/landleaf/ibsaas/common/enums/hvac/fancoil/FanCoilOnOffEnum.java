package com.landleaf.ibsaas.common.enums.hvac.fancoil;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/13 17:59
 * @description:
 */
public enum FanCoilOnOffEnum {

    /**
     * 风盘开关机状态
     */
    OFF(0, "关机"),
    ON(1, "开机"),
    STANDBY(2, "待机"),
    ;



    private Integer state;

    private String description;

    FanCoilOnOffEnum(Integer state, String description) {
        this.state = state;
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<ChoiceButton> getChoiceButtons() {
        List<ChoiceButton> choiceButtons = new ArrayList<>();
        for (FanCoilOnOffEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
