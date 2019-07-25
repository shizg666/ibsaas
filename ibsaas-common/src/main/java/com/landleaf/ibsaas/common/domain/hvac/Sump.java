package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:00
 * @description: 集水坑
 */
@Data
@ApiModel("集水坑")
public class Sump extends BaseDevice implements Serializable {

    @ApiModelProperty("集水坑-1号泵运行状态")
    private String spOnePumpRunningState;

    @ApiModelProperty("集水坑-1号泵故障状态")
    private String spOnePumpMalState;

    @ApiModelProperty("集水坑-2号泵运行状态")
    private String spTwoPumpRunningState;

    @ApiModelProperty("集水坑-2号泵故障状态")
    private String spTwoPumpMalState;

    @ApiModelProperty("集水坑-高液位报警")
    private String spHighLiqLvlAlarm;

    @ApiModelProperty("集水坑-低液位报警")
    private String spLowLiqLvlAlarm;
}
