package com.landleaf.ibsaas.common.domain.energy.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("统计报表返回对象")
public class EnergyReportResponseVO {

    @ApiModelProperty("时间")
    private String timeValue;
    @ApiModelProperty("能耗")
    private String energyValue;
    @ApiModelProperty("类型值")
    private String typeValue;
    @ApiModelProperty("查询类型")
    private Integer queryType;

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

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
