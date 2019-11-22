package com.landleaf.ibsaas.common.domain.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 假期记录表
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
@Data
@Accessors(chain = true)
@ApiModel(value="VacationSetting对象", description="假期记录表")
public class VacationSetting {

    private Long id;

    @ApiModelProperty(value = "节假日类型 1-放假 2-补班")
    private Integer type;

    @ApiModelProperty(value = "日期")
    private LocalDate day;

    @ApiModelProperty(value = "节日描述")
    private String comment;

    @ApiModelProperty(value = "对应目标类型")
    private Integer targetType;

    @ApiModelProperty(value = "对应值")
    private String target;


}
