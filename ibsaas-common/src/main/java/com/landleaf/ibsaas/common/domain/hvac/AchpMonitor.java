package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/11 14:30
 * @description:
 */
@Data
@ApiModel("风冷热泵-监测")
public class AchpMonitor extends BaseDevice implements Serializable {

    @ApiModelProperty("风冷热泵-监测-供水温度")
    private String amGoWaterTemp;

    @ApiModelProperty("风冷热泵-监测-供水压力")
    private String amGoWaterPress;

    @ApiModelProperty("风冷热泵-监测-定压装置运行状态")
    private String amConstPressRunningState;

    @ApiModelProperty("风冷热泵-监测-定压装置故障状态")
    private String amConstPressMalfunctionState;

    @ApiModelProperty("风冷热泵-监测-旁通阀开度反馈")
    private String amBypassValveFeedback;

    @ApiModelProperty("风冷热泵-监测-旁通阀开度设定")
    private String amBypassValveSetting;

    @ApiModelProperty("风冷热泵-监测-加药装置运行状态")
    private String amDosingRunningState;

    @ApiModelProperty("风冷热泵-监测-加药装置故障状态")
    private String amDosingMalfunctionState;

    @ApiModelProperty("风冷热泵-监测-回水流量")
    private String amBackWaterFlux;

    @ApiModelProperty("风冷热泵-监测-回水温度")
    private String amBackWaterTemp;

    @ApiModelProperty("风冷热泵-监测-回水压力")
    private String amBackWaterPress;
}
