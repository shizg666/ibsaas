package com.landleaf.ibsaas.common.domain.hvac.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/3 14:53
 * @description: 节点字段对象
 */
@Data
public class HvacNodeFieldVO implements Serializable {

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("连接id")
    private String deviceId;

    @ApiModelProperty("点位类型")
    private String bacnetObjectType;

    @ApiModelProperty("点位值")
    private Integer instanceNumber;

    @ApiModelProperty("点位权限")
    private Integer permission;
}
