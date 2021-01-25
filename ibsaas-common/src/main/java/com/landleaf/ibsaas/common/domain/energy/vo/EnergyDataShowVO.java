package com.landleaf.ibsaas.common.domain.energy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/5/28 8:54
 * @description:
 */
@ApiModel("前端能耗数据展示")
@Data
public class EnergyDataShowVO {


    @ApiModelProperty("主键id")
    private Long id;


    @ApiModelProperty("时间")
    private Long time;

    @ApiModelProperty("能耗数值")
    private BigDecimal value;


    //    @ApiModelProperty("创建时间")
//    private Date ctime;
//
//    @ApiModelProperty("更新时间")
//    private Date utime;
}
