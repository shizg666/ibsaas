package com.landleaf.ibsaas.common.domain.light;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
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
public class SceneTimingDTO {


    private Long id;

    @ApiModelProperty(value = "场景code")
    private String code;

    @ApiModelProperty(value = "区域code")
    private String adress;

    @ApiModelProperty(value = "几楼")
    private String floor;

    @ApiModelProperty(value = "0 否 1是")
    private Integer skipHolidayFlag;



}
