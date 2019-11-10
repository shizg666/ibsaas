package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 14:26
 * @description:    风机盘管
 */
@Data
@ApiModel("风机盘管")
public class FanCoil extends BaseDevice implements Serializable {

    /**
     * 风机盘管通信状态
     */
    @ApiModelProperty("风机盘管通信状态")
    private String fcComState;

    /**
     * 风机盘管水阀状态
     */
    @ApiModelProperty("风机盘管水阀状态")
    private String fcWaterValveState;

    /**
     * 风机盘管回风温度(室内温度)
     */
    @ApiModelProperty("风机盘管回风温度(室内温度)")
    private String fcBackTemp;

    /**
     * 风机盘管开关机
     */
    @ApiModelProperty("风机盘管开关机")
    private String fcOnOff;

    /**
     * 风机盘管运行模式
     */
    @ApiModelProperty("风机盘管运行模式")
    private String fcRunningMode;

    /**
     * 风机盘管温度设定
     */
    @ApiModelProperty("风机盘管温度设定")
    private String fcTempSetting;

    /**
     * 风机盘管风机模式
     */
    @ApiModelProperty("风机盘管风机模式")
    private String fcMachMode;



    /**
     * 风机盘管高速
     */
    @ApiModelProperty("风机盘管高速")
    private String fcHighSpeed;

    /**
     * 风机盘管中速
     */
    @ApiModelProperty("风机盘管中速")
    private String fcMediumSpeed;

    /**
     * 风机盘管低速
     */
    @ApiModelProperty("风机盘管低速")
    private String fcLowSpeed;


}
