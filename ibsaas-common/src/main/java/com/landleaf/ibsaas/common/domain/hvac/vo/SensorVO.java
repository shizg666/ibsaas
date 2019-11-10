package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.Sensor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 17:14
 * @description:
 */
@Data
@ToString(callSuper = true)
public class SensorVO extends Sensor implements Serializable {

    @ApiModelProperty("甲醛等级")
    private String ssHchoLevel;
}
