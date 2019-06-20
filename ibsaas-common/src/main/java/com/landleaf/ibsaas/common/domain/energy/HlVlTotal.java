package com.landleaf.ibsaas.common.domain.energy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lokiy
 * @date 2019/6/20 11:20
 * @description:
 */
@Data
@ApiModel("总报表对象")
@AllArgsConstructor
@NoArgsConstructor
public class HlVlTotal {

    @ApiModelProperty("报表图key")
    private String reportKey;

    @ApiModelProperty("报表")
    private HlVl hlVl;
}
