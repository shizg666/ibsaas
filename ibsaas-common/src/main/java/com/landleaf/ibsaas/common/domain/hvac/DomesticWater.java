package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:05
 * @description:
 */
@Data
@ApiModel("生活水")
public class DomesticWater extends BaseDevice implements Serializable {

    @ApiModelProperty("生活水-高水位报警")
    private String dwHighLiqLvlAlarm;

    @ApiModelProperty("生活水-低水位报警")
    private String dwLowLiqLvlAlarm;

    @ApiModelProperty("生活水-1#运行状态")
    private String dwOnePumpRunningState;

    @ApiModelProperty("生活水-1#故障状态")
    private String dwOnePumpMalState;

    @ApiModelProperty("生活水-1#启动控制")
    private String dwOnePumpOnOff;

    @ApiModelProperty("生活水-2#运行状态")
    private String dwTwoPumpRunningState;

    @ApiModelProperty("生活水-2#故障状态")
    private String dwTwoPumpMalState;

    @ApiModelProperty("生活水-2#启动控制")
    private String dwTwoPumpOnOff;
}
