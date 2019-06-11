package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 18:02
 * @description:
 */
@Data
@ApiModel("水表")
public class WaterMeter extends BaseDevice implements Serializable {

    /**
     * 水表通信状态
     */
    @ApiModelProperty("水表通信状态")
    private String wmComState;

    /**
     * 水表读数
     */
    @ApiModelProperty("水表读数")
    private String wmReading;
}
