package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 17:55
 * @description: 雨水收集
 */
@Data
@ApiModel("雨水收集")
public class RainwaterCollection extends BaseDevice implements Serializable {
}
