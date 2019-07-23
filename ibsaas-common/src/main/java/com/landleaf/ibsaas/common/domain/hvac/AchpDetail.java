package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/10 10:27
 * @description: AirCooledHeatPump 风冷热泵
 */
@Data
@ApiModel("风冷热泵详细参数")
public class AchpDetail extends BaseDevice implements Serializable {

    @ApiModelProperty("开关机状态")
    private String adOnOffState;

    @ApiModelProperty("运行模式状态")
    private String adRunningMode;

    @ApiModelProperty("泵运行状态")
    private String adPumpState;

    @ApiModelProperty("压缩机运行状态")
    private String adCompressorState;

    @ApiModelProperty("模块通讯状态")
    private String adComState;

    @ApiModelProperty("模块X故障状态")
    private String adMalfunctionState;

    @ApiModelProperty("系统回水温度")
    private String adSysBackWaterTemp;

    @ApiModelProperty("系统出水温度")
    private String adSysGoWaterTemp;

    @ApiModelProperty("模块X出水温度")
    private String adModGoWaterTemp;

    @ApiModelProperty("模块1压机X-1排气温度")
    private String adModPressOneExhaustTemp;

    @ApiModelProperty("模块1压机X-2排气温度")
    private String adModPressTwoExhaustTemp;

    @ApiModelProperty("模块X吸气压力")
    private String adModInhalePressure;

    @ApiModelProperty("模块X排气压力")
    private String adModExhaustPressure;

    @ApiModelProperty("启停控制")
    private String adOnOff;

    @ApiModelProperty("运行模式设定")
    private String adRunningModeSetting;

    @ApiModelProperty("制冷回水温度设定")
    private String adColdBackWaterTempSetting;

    @ApiModelProperty("制热回水温度设定")
    private String adHotBackWaterTempSetting;

    @ApiModelProperty("制冷出水温度设定")
    private String adColdGoWaterTempSetting;

    @ApiModelProperty("制热出水温度设定")
    private String adHotGoWaterTempSetting;

    @ApiModelProperty("故障复位")
    private String adMalfunctionReset;

    @ApiModelProperty("制冷控制选择设定值")
    private String adColdContSetting;

    @ApiModelProperty("制热控制选择设定值")
    private String adHotContSetting;

}
