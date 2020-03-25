package com.landleaf.ibsaas.common.domain.light.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

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
@ToString
@ApiModel(description = "LightSceneTimingRespVO")
public class LightSceneTimingRespVO {

    @ApiModelProperty(value = "主键id")
    private Long id;


    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "场景code")
    private String code;


    @ApiModelProperty(value = "场景id")
    private Long attributeId;


    @ApiModelProperty(value = "执行时间")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date time;

    @ApiModelProperty(value = "0 关 1 开")
    private Integer switchFlag;

    @ApiModelProperty(value = "0 否 1是")
    private Integer skipHolidayFlag;

}
