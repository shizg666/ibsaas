package com.landleaf.ibsaas.common.enums.hvac.achp;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/10 14:57
 * @description:
 */
public enum  AchpDetailRunningModeEnum {

    /**
     * 风冷热泵详情运行模式
     */
    HOT_MODE(0, "热水模式"),
    COLD_MODE(1, "冷水模式"),
    ONLY_PUMP_MODE(3, "仅用泵模式"),
    ;

    private Integer state;

    private String description;

    AchpDetailRunningModeEnum(Integer state, String description) {
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
        for (AchpDetailRunningModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
