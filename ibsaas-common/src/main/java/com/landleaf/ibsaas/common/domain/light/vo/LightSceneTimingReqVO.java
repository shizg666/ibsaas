package com.landleaf.ibsaas.common.domain.light.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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
@ApiModel(description = "LightSceneTimingReqVO")
@Data
@ToString
public class LightSceneTimingReqVO {

    @ApiModelProperty(value = "主键Id(修改毕传)")
    private Long id;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "t_light_attribute (场景id)")
    @NotNull(message = "场景不能为空")
    private Integer attributeId;

    @ApiModelProperty( value = "执行时间 时间格式- HH:mm", required = true)
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date time;

    @ApiModelProperty(value = "0 否 1是")
    private Integer skipHolidayFlag;

    @ApiModelProperty(value = "0 关 1 开")
    private Integer switchFlag;

}
