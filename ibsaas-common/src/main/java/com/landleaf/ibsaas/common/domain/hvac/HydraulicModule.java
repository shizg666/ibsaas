package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 17:17
 * @description:
 */
@Data
@ApiModel("水力模块")
public class HydraulicModule extends BaseDevice implements Serializable {


    /**
     * 水力模块机组通信状态
     */
    @ApiModelProperty("水力模块机组通信状态")
    private String hmMachComState;

    /**
     * 水力模块机组水阀状态
     */
    @ApiModelProperty("水力模块机组水阀状态")
    private String hmMachWaterValveState;

    /**
     * 水力模块机组开关状态
     */
    @ApiModelProperty("水力模块机组开关状态")
    private String hmMachOnOff;

    /**
     * 水力模块机组运行模式
     */
    @ApiModelProperty("水力模块机组运行模式")
    private String hmMachRunningMode;

    /**
     * 水力模块机组温度设定
     */
    @ApiModelProperty("水力模块机组温度设定")
    private String hmMachTempSetting;

    /**
     * 水力模块机组环境温度
     */
    @ApiModelProperty("水力模块机组环境温度")
    private String hmMachEnvTemp;

    /**
     * 水力模块防结露通信状态
     */
    @ApiModelProperty("水力模块防结露通信状态")
    private String hmPdewComState;

    /**
     * 水力模块防结露水阀状态
     */
    @ApiModelProperty("水力模块防结露水阀状态")
    private String hmPdewWaterValveState;

    /**
     * 水力模块防结露环境温度
     */
    @ApiModelProperty("水力模块防结露环境温度")
    private String hmPdewEnvTemp;

    /**
     * 水力模块防结露相对湿度
     */
    @ApiModelProperty("水力模块防结露相对湿度")
    private String hmPdewRelaHum;

    /**
     * 水力模块防结露温度
     */
    @ApiModelProperty("水力模块防结露温度")
    private String hmPdewTemp;

    /**
     * 水力模块防结露外置温感温度
     */
    @ApiModelProperty("水力模块防结露外置温感温度")
    private String hmPdewOsFeelTemp;

    /**
     * 水力模块防结露系统启动
     */
    @ApiModelProperty("水力模块防结露系统启动")
    private String hmPdewOnOff;

    /**
     * 水力模块防结露运行模式设定
     */
    @ApiModelProperty("水力模块防结露运行模式设定")
    private String hmPdewRunningMode;

    /**
     * 水力模块防结露当前模式温度设定
     */
    @ApiModelProperty("水力模块防结露当前模式温度设定")
    private String hmPdewCurTempSetting;

    /**
     * 水力模块防结露制热模式温度设定
     */
    @ApiModelProperty("水力模块防结露制热模式温度设定")
    private String hmPdewHotTempSetting;

    /**
     * 水力模块防结露制冷模式温度设定
     */
    @ApiModelProperty("水力模块防结露制冷模式温度设定")
    private String hmPdewColdTempSetting;
}
