package com.landleaf.ibsaas.common.domain.energy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lokiy
 * @date 2019/6/20 10:55
 * @description:
 */
@Data
@ApiModel("查询出来的横纵坐标原始数据")
@AllArgsConstructor
@NoArgsConstructor
public class HlVlBO {

    @ApiModelProperty("横坐标日期")
    private String x;

    @ApiModelProperty("横坐标对应的纵坐标的值")
    private String y;

}
