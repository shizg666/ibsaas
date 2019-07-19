package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:07
 * @description:
 */
@Data
@ApiModel("排风机")
public class ExhaustBlower extends BaseDevice implements Serializable {

    @ApiModelProperty("排风机-运行状态")
    private String ebRunningState;

    @ApiModelProperty("排风机-故障状态")
    private String ebMalState;

    @ApiModelProperty("排风机-启停控制")
    private String ebOnOff;

    @ApiModelProperty("排风机-现场模式")
    private String ebLiveMode;
}
