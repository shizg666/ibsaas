package com.landleaf.ibsaas.common.enums.hvac.achp;

import com.landleaf.ibsaas.common.domain.ChoiceButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/10 15:23
 * @description:
 */
public enum AchpDetailOnOffEnum {
    /**
     * 风冷热泵详情启动控制
     */
    INVALID(0, "无效"),
    ON(1, "开机"),
    OFF(2, "关机"),
    ;

    private Integer state;

    private String description;

    AchpDetailOnOffEnum(Integer state, String description) {
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
        for (AchpDetailOnOffEnum en:values()){
            choiceButtons.add(new ChoiceButton(String.valueOf(en.state), en.description));
        }
        return choiceButtons;
    }
}
