package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/5/28 15:29
 * @description: 设备基类 用于识别
 */
@Data
public class BaseDevice implements Serializable {

    /**
     * 某项设备主键id
     */
    @ApiModelProperty("某项设备主键id")
    private String id;
}
