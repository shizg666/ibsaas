package com.landleaf.ibsaas.common.domain.energy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/6/17 17:51
 * @description:
 */
@Data
@ApiModel("水电表读数和增长值辅助对象")
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDataValueDTO {

    @ApiModelProperty("读数")
    private BigDecimal dataValue;

    @ApiModelProperty("增长值")
    private BigDecimal increaseDataValue;

}
