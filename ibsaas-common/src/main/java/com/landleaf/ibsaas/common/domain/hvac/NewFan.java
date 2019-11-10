package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/5/24 16:08
 * @description: 四效新风传输对象
 */
@Data
@ApiModel("四效新风")
public class NewFan extends BaseDevice implements Serializable {

    /**
     * 新风温度(单位:℃）
     * newWindTemperature
     */
    @ApiModelProperty("新风温度(单位:℃）")
    private String newWindTemp;

    /**
     * 新风湿度(单位:%)
     * newWindHumidity
     */
    @ApiModelProperty("新风湿度(单位:%)")
    private String newWindHum;

    /**
     * 送风温度(单位:℃）
     * goWindTemperature
     */
    @ApiModelProperty("送风温度(单位:℃）")
    private String goWindTemp;

    /**
     * 送风湿度(单位:%)
     * goWindHumidity
     */
    @ApiModelProperty("送风湿度(单位:%)")
    private String goWindHum;

    /**
     * 回风温度(单位:℃）
     * backWindTemperature
     */
    @ApiModelProperty("回风温度(单位:℃）")
    private String backWindTemp;

    /**
     * 回风湿度(单位:%)
     * backWindHumidity
     */
    @ApiModelProperty("回风湿度(单位:%)")
    private String backWindHum;

    /**
     * 滤网使用小时
     * filterScreenUsingTime
     */
    @ApiModelProperty("滤网使用小时")
    private String screenUsingTime;

    /**
     * 外机频率
     * outsideMachineFrequency
     */
    @ApiModelProperty("外机频率")
    private String osMachFrequency;

    /**
     * 夏季模式送风温度设定
     * summerModeGoWindTemperatureSetting
     */
    @ApiModelProperty("夏季模式送风温度设定")
    private String summerGoWindTempSetting;

    /**
     * 除湿模式送风温度设定
     * dehumidificationModeGoWindTemperatureSetting
     */
    @ApiModelProperty("除湿模式送风温度设定")
    private String dehumGoWindTempSetting;

    /**
     * 冬季模式送分温度设定
     * winterModeGoWindTemperatureSetting
     */
    @ApiModelProperty("冬季模式送分温度设定")
    private String winterGoWindTempSetting;

    /**
     * 送风机转速设定
     * blowerSpeedSetting
     */
    @ApiModelProperty("送风机转速设定")
    private String blowerSpeedSetting;

    /**
     * 排风机转速设定
     * exhaustBlowerSpeedSetting
     */
    @ApiModelProperty("排风机转速设定")
    private String exhaustBlowerSpeedSetting;

    /**
     * 单外机环境温度限制设定
     * singleOutsideMachineEnvironmentTemperatureLimitSetting
     */
    @ApiModelProperty("单外机环境温度限制设定")
    private String singleOsMachEnvTempLmtSetting;

    /**
     * 机组故障状态(1:正常 0:非正常)
     * malfunctionState
     */
    @ApiModelProperty("机组故障状态(1:正常 0:非正常)")
    private String malfunctionState;

    /**
     * 机组运行状态(1:运行 0:关闭)
     * runningState
     */
    @ApiModelProperty("机组运行状态(1:运行 0:关闭)")
    private String runningState;

    /**
     * 内机状态
     * insideMachineState
     */
    @ApiModelProperty("内机状态")
    private String isMachState;

    /**
     * 加湿阀状态
     * humidificationElectrovalveState
     */
    @ApiModelProperty("加湿阀状态")
    private String humElectrovalveState;

    /**
     * 机组开关机
     * onOff
     */
    @ApiModelProperty("机组开关机")
    private String onOff;

    /**
     * 加湿开关(1:开 0:关)
     * humidificationOnOff
     */
    @ApiModelProperty("加湿开关(1:开 0:关)")
    private String humOnOff;

    /**
     * 加湿湿度值
     */
    @ApiModelProperty("加湿湿度值")
    private String humSetting;

    /**
     * 清空滤网计时(1:清空 0:检测中)
     * emptyFilterScreenTiming
     */
    @ApiModelProperty("清空滤网计时(1:清空 0:检测中)")
    private String emptyScreenTiming;

    /**
     * 运行模式(1:夏季 2:除湿 3:冬季 4:通风 5:智能)
     * runningMode
     */
    @ApiModelProperty("运行模式(1:夏季 2:除湿 3:冬季 4:通风 5:智能)")
    private String runningMode;
}
