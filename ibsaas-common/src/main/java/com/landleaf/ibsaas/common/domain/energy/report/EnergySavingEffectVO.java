package com.landleaf.ibsaas.common.domain.energy.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/6/21 16:50
 * @description: 节点效果数值
 */
@Data
@ApiModel("节能效果数值")
@AllArgsConstructor
@NoArgsConstructor
public class EnergySavingEffectVO {

    @ApiModelProperty("已节能能耗")
    private BigDecimal alreadySavingValue;

    @ApiModelProperty("节能率")
    private String savingProportional;

    @ApiModelProperty("国标")
    private BigDecimal chineseStandard;
}
