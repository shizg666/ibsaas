package com.landleaf.ibsaas.common.domain.energy.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lokiy
 * @date 2019/6/20 16:53
 * @description: 比例数据
 */
@Data
@ApiModel("比例数据")
@AllArgsConstructor
@NoArgsConstructor
public class ProportionalData {

    @ApiModelProperty("先前数据")
    private String prev;

    @ApiModelProperty("当前数据")
    private String current;
}
