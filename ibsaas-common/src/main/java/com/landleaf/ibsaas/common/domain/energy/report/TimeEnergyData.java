package com.landleaf.ibsaas.common.domain.energy.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/6/23 21:31
 * @description:
 */
@Data
@ApiModel("时间能耗数据")
public class TimeEnergyData {

    @ApiModelProperty("时间区间")
    private String time;

    @ApiModelProperty("时间内的能耗")
    private BigDecimal energyData;
}
