package com.landleaf.ibsaas.common.enums.hvac.newfan;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 17:31
 * @description: 四效新风机运行模式枚举
 */
public enum  NewFanRunningModeEnum {
    /**
     * 四效新风模式
     */
    SUMMER_MODE(1, "夏季"),

    DEHUMIDIFICATION_MODE(2, "除湿"),

    WINNER_MODE(3, "冬季"),

    VENTILATION_MODE(4, "通风"),

    SMART_MODE(5, "智能"),

    ;


    private Integer state;

    private String description;

    NewFanRunningModeEnum(Integer state, String description) {
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
        for (NewFanRunningModeEnum nfrme:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(nfrme.state), nfrme.description));
        }
        return choiceButtons;
    }
}
