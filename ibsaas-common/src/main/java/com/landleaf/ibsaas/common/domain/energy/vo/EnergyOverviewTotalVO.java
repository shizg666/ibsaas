package com.landleaf.ibsaas.common.domain.energy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/6/18 17:21
 * @description: 累计能耗对象
 */
@Data
@ApiModel("累计能耗")
public class EnergyOverviewTotalVO implements Serializable {

    @ApiModelProperty("建筑面积")
    private BigDecimal buildingAcreage;

    @ApiModelProperty("当日能耗")
    private BigDecimal currentDayData;

    @ApiModelProperty("当周能耗")
    private BigDecimal currentWeekData;

    @ApiModelProperty("当月能耗")
    private BigDecimal currentMonthData;

    @ApiModelProperty("当年能耗")
    private BigDecimal currentYearData;

    @ApiModelProperty("建筑单位面积能耗(年)")
    private BigDecimal perAcreageData;

}
