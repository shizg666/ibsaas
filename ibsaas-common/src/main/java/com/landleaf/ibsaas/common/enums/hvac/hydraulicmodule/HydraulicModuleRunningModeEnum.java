package com.landleaf.ibsaas.common.enums.hvac.hydraulicmodule;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.enums.hvac.fancoil.FanCoilRunningModeExEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/14 11:04
 * @description:
 */
public enum  HydraulicModuleRunningModeEnum {

    /**
     * 运行模式
     */
    CLOD_MODE(1, "制冷"),
    HOT_MODE(2, "制热"),
    AUTO_MODE(3, "自动"),

    ;

    private Integer state;

    private String description;

    HydraulicModuleRunningModeEnum(Integer state, String description) {
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
        for (HydraulicModuleRunningModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }



}
