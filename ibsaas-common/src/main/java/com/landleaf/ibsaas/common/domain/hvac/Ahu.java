package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/2 17:24
 * @description:
 */
@Data
@ApiModel("Ahu")
public class Ahu extends BaseDevice implements Serializable {

    @ApiModelProperty("回风湿度")
    private String ahuBackWindHum;

    @ApiModelProperty("回风温度")
    private String ahuBackWindTemp;

    @ApiModelProperty("水阀开度反馈")
    private String ahuWaterValveFeedback;

    @ApiModelProperty("频率调节控制")
    private String ahuFrequencyAdjust;

    @ApiModelProperty("湿度设定")
    private String ahuHumSetting;

    @ApiModelProperty("温度设定")
    private String ahuTempSetting;

    @ApiModelProperty("水阀调节控制")
    private String ahuWaterValveAdjust;

    @ApiModelProperty("新风阀")
    private String ahuNewFanValve;

    @ApiModelProperty("风机手自动")
    private String ahuHandAutomatically;

    @ApiModelProperty("风机运行状态")
    private String ahuRunningState;

    @ApiModelProperty("风机故障状态")
    private String ahuMalfunctionState;

    @ApiModelProperty("初效过滤网")
    private String ahuPrimaryFilter;

    @ApiModelProperty("中效过滤网")
    private String ahuMediumFilter;

    @ApiModelProperty("防冻状态")
    private String ahuAntifreezeState;

    @ApiModelProperty("季节模式")
    private String ahuSeasonMode;

    @ApiModelProperty("水阀控制模式")
    private String ahuWaterValveMode;

    @ApiModelProperty("风机启停")
    private String ahuOnOff;

    @ApiModelProperty("加湿阀状态")
    private String ahuHumValveState;

    @ApiModelProperty("送风湿度")
    private String ahuGoWindHum;

    @ApiModelProperty("频率反馈")
    private String ahuFrequencyFeedback;

    @ApiModelProperty("送风温度")
    private String ahuGoWingTemp;

}
