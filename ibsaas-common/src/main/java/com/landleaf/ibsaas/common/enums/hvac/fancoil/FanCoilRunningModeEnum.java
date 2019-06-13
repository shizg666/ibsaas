package com.landleaf.ibsaas.common.enums.hvac.fancoil;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/13 17:52
 * @description:
 */
public enum  FanCoilRunningModeEnum {

    /**
     * 运行模式
     */
    CLOD_MODE(1, "制冷"),
    HOT_MODE(2, "制热"),
    WIND_MODE(3, "送风"),
    AUTO_MODE(4, "自动模式"),
    AUTO_HOT_MODE(5, "自动制热"),

    ;

    private Integer state;

    private String description;

    FanCoilRunningModeEnum(Integer state, String description) {
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
        for (FanCoilRunningModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
