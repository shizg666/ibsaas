package com.landleaf.ibsaas.common.enums.hvac.ahu;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/4 14:11
 * @description:
 */
public enum AhuSeasonModeEnum {

    /**
     * ahu季节模式
     */
    SUMMER_MODE(0, "夏季"),
    WINTER_MODE(1, "冬季"),
    ;

    private Integer state;

    private String description;

    AhuSeasonModeEnum(Integer state, String description) {
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
        for (AhuSeasonModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
