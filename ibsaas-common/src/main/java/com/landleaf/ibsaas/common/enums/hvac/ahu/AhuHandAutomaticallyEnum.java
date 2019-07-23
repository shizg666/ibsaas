package com.landleaf.ibsaas.common.enums.hvac.ahu;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/4 14:17
 * @description:
 */
public enum  AhuHandAutomaticallyEnum {

    /**
     * ahu手自动
     */
    AUTO_MODE(0, "自动"),
    HAND_MODE(1, "手动"),
    ;

    private Integer state;

    private String description;

    AhuHandAutomaticallyEnum(Integer state, String description) {
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
        for (AhuHandAutomaticallyEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
