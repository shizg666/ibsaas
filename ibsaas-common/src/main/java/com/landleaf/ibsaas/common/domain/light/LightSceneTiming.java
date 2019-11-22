package com.landleaf.ibsaas.common.domain.light;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
@Data
public class LightSceneTiming{


    private Long id;

    @ApiModelProperty(value = "区域id")
    private Long deviceId;

    @ApiModelProperty(value = "t_light_attribute (场景id)")
    private Integer attributeId;

    @ApiModelProperty(value = "执行时间")
    private Date time;

    @ApiModelProperty(value = "0 关 1 开")
    private Integer switchFlag;

    @ApiModelProperty(value = "0 否 1是")
    private Integer skipHolidayFlag;

    private LocalDateTime ct;

    private LocalDateTime ut;


}
