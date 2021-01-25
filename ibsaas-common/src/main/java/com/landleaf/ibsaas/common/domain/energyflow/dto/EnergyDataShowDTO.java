package com.landleaf.ibsaas.common.domain.energyflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/5/28 8:54
 * @description:
 */
@ApiModel("前端能耗数据展示")
@Data
public class EnergyDataShowDTO  {


    @ApiModelProperty("主键id")
    private Long id;


    @ApiModelProperty( value = "时间", required = true)
    private Long time;

    @ApiModelProperty("能耗数值")
    private BigDecimal value;
}
