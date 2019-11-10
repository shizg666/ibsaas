package com.landleaf.ibsaas.common.domain.energy.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lokiy
 * @date 2019/6/27 14:47
 * @description:
 */
@Data
@ApiModel("年日")
public class DayOfYear {

    @ApiModelProperty("年")
    private Integer year;

    @ApiModelProperty("日")
    private Integer days;
}
