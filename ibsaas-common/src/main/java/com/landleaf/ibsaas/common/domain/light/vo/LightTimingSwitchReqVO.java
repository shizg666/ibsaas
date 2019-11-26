package com.landleaf.ibsaas.common.domain.light.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
@ApiModel(description = "LightTimingSwitchReqVO")
@Data
@ToString
public class LightTimingSwitchReqVO {

    @ApiModelProperty(value = "定时主键id")
    private Long id;

    @ApiModelProperty(value = "0 关 1 开")
    private Integer switchFlag;

}
