package com.landleaf.ibsaas.common.domain.energy.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("统计报表返回对象")
public class EnergyReportResponseVO {

    @ApiModelProperty("时间")
    private String timeValue;
    @ApiModelProperty("能耗")
    private String energyValue;
    @ApiModelProperty("能耗所属分类")
    private Integer equipClassification;

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(String energyValue) {
        this.energyValue = energyValue;
    }

    public Integer getEquipClassification() {
        return equipClassification;
    }

    public void setEquipClassification(Integer equipClassification) {
        this.equipClassification = equipClassification;
    }
}
