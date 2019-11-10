package com.landleaf.ibsaas.common.enums.hvac.fancoil;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/13 18:04
 * @description:
 */
public enum FanCoilMachModeExEnum {

    /**
     * 风机模式
     */
    High_speed(1, "高速"),
    Medium_Speed(2, "中速"),
    Low_Speed(3, "低速"),
    AUTO(0, "自动"),

    ;

    private Integer state;

    private String description;

    FanCoilMachModeExEnum(Integer state, String description) {
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
        for (FanCoilMachModeExEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
