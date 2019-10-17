package com.landleaf.ibsaas.common.enums.hvac.achp;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/10 15:23
 * @description:
 */
public enum AchpDetailContSettingEnum {
    /**
     * 风冷热泵详情启动控制
     */
    CHANGE_WATER(0, "变出水温度控制"),
    BACK_WATER(1, "回水定水温控制"),
    GO_WATER(2, "出水定水温控制"),
    ;

    private Integer state;

    private String description;

    AchpDetailContSettingEnum(Integer state, String description) {
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
        for (AchpDetailContSettingEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
