package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 18:07
 * @description:
 */
@Data
@ApiModel("电表")
public class ElectricMeter extends BaseDevice implements Serializable {

    /**
     * 电表通信状态
     */
    @ApiModelProperty("电表通信状态")
    private String emComState;

    /**
     * 电表读数
     */
    @ApiModelProperty("电表读数")
    private String emReading;
}
