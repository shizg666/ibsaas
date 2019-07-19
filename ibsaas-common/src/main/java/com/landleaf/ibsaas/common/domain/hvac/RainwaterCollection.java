package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 17:55
 * @description: 雨水收集
 */
@Data
@ApiModel("雨水收集")
public class RainwaterCollection extends BaseDevice implements Serializable {


    @ApiModelProperty("雨水收集-系统手自动状态")
    private String rcSysHandAutoState;

    @ApiModelProperty("雨水收集-1#原水泵故障状态")
    private String rcOneOriPumpMalState;

    @ApiModelProperty("雨水收集-2#原水泵故障状态")
    private String rcTwoOriPumpMalState;

    @ApiModelProperty("雨水收集-1#送水泵故障状态")
    private String rcOneGoPumpMalState;

    @ApiModelProperty("雨水收集-2#送水泵故障状态")
    private String rcTwoGoPumpMalState;

    @ApiModelProperty("雨水收集-1#原水泵运行状态")
    private String rcOneOriPumpRunningState;

    @ApiModelProperty("雨水收集-2#原水泵运行状态")
    private String rcTwoOriPumpRunningState;

    @ApiModelProperty("雨水收集-1#送水泵运行状态")
    private String rcOneGoPumpRunningState;

    @ApiModelProperty("雨水收集-2#送水泵运行状态")
    private String rcTwoGoPumpRunningState;

    @ApiModelProperty("雨水收集-原水箱低中液位")
    private String rcOriCisternLmLiqLvl;

    @ApiModelProperty("雨水收集-原水箱中高液位")
    private String rcOriCisternMhLiqLvl;

    @ApiModelProperty("雨水收集-送水系统自动")
    private String rcGoWaterSysAuto;

    @ApiModelProperty("雨水收集-送水系统高频")
    private String rcGoWaterSysHighFreq;

    @ApiModelProperty("雨水收集-送水系统低频")
    private String rcGoWaterSysLowFreq;

    @ApiModelProperty("雨水收集-回收水箱低中液位")
    private String rcBackCisternLmLiqLvl;

    @ApiModelProperty("雨水收集-回收水箱中高液位")
    private String rcBackCisternMhLiqLvl;

    @ApiModelProperty("雨水收集-进水检测雨水")
    private String rcInflowDetection;


}
