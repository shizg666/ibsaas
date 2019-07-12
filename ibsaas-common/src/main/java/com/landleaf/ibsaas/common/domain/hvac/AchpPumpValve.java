package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/11 11:15
 * @description:
 */
@Data
@ApiModel("风冷热泵-水泵水阀")
public class AchpPumpValve extends BaseDevice implements Serializable {

    @ApiModelProperty("风冷热泵-水泵-运行状态")
    private String apRunningState;

    @ApiModelProperty("风冷热泵-水泵-故障状态")
    private String apMalfunctionState;

    @ApiModelProperty("风冷热泵-水泵-启停控制")
    private String apOnOff;

    @ApiModelProperty("风冷热泵-水泵-变频控制")
    private String apFreCont;

    @ApiModelProperty("风冷热泵-水泵-变频反馈")
    private String apFreFeedback;

    @ApiModelProperty("风冷热泵-水泵-手自动状态")
    private String apHandAutoState;



    @ApiModelProperty("风冷热泵-水阀-运行状态")
    private String avRunningState;

    @ApiModelProperty("风冷热泵-水阀-启停控制")
    private String avOnOff;




    @ApiModelProperty("风冷热泵-水流-运行状态")
    private String asRunningMode;
}
