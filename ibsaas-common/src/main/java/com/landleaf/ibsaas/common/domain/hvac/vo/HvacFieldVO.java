package com.landleaf.ibsaas.common.domain.hvac.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/5/28 15:02
 * @description: 字段vo
 */
@Data
@ApiModel("设备字段")
public class HvacFieldVO implements Serializable {

    /**
     * 设备字段主键id
     */
    @ApiModelProperty("设备字段主键id")
    private String id;

    /**
     * 设备id
     */
    @ApiModelProperty("设备类型")
    private Integer deviceType;

    /**
     * 字段名称
     */
    @ApiModelProperty("字段名称")
    private String fieldName;

    /**
     * 字段描述
     */
    @ApiModelProperty("字段描述")
    private String fieldDescription;

    /**
     * 读写权限
     */
    @ApiModelProperty("读写权限")
    private Integer permission;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("连接id")
    private String deviceId;

    /**
     * bacnet对象类型
     */
    @ApiModelProperty("对象类型")
    private String bacnetObjectType;

    /**
     * 设备号
     */
    @ApiModelProperty("设备号")
    private Integer instanceNumber;
}
