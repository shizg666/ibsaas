package com.landleaf.ibsaas.common.enums.hvac.fancoil;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.enums.hvac.newfan.NewFanRunningModeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/13 18:04
 * @description:
 */
public enum  FanCoilMachModeEnum {

    /**
     * 风机模式
     */
    ONE_WIND(1, "一档风"),
    TWO_WIND(2, "二档风"),
    THREE_WIND(3, "三档风"),
    FOUR_WIND(4, "四档风"),
    FIVE_WIND(5, "五档风"),
    AUTO_ONE_WIND(6, "自动一档"),
    AUTO_TWO_WIND(7, "自动二档"),
    AUTO_THREE_WIND(8, "自动三档"),
    AUTO_FOUR_WIND(9, "自动四档"),
    AUTO_FIVE_WIND(10, "自动五档"),
    AUTO_TOP_WIND(11, "自动停风"),
    ;

    private Integer state;

    private String description;

    FanCoilMachModeEnum(Integer state, String description) {
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
        for (FanCoilMachModeEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
