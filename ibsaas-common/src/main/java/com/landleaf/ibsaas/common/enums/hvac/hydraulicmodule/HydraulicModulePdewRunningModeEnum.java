package com.landleaf.ibsaas.common.enums.hvac.hydraulicmodule;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/14 11:04
 * @description:
 */
public enum HydraulicModulePdewRunningModeEnum {

    /**
     * 运行模式
     */
    CLOD_MODE(0, "制冷"),
    HOT_MODE(1, "制热"),

    ;

    private Integer state;

    private String description;

    HydraulicModulePdewRunningModeEnum(Integer state, String description) {
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
        for (HydraulicModulePdewRunningModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }



}
