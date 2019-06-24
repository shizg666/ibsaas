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
@ApiModel("间隔数据")
public class IntervalData {

    @ApiModelProperty("时间值")
    private String timeInterval;

    @ApiModelProperty("时间内的数据")
    private BigDecimal intervalValue;
}
