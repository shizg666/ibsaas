package com.landleaf.ibsaas.common.domain.hvac.assist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lokiy
 * @date 2019/7/2 17:53
 * @description:
 */
@Data
@ApiModel("点位详情")
public class HvacPointDetail {

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("设备类型")
    private Integer deviceType;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("设备id")
    private String deviceId;


    @ApiModelProperty("远程设备网关ip")
    private String ip;


    @ApiModelProperty("远程设备端口")
    private Integer port;


    @ApiModelProperty("远程设备设备号")
    private Integer deviceInstanceNumber;


    @ApiModelProperty("设备描述")
    private String description;


    @ApiModelProperty("网络路由")
    private Integer networkNumber;


    @ApiModelProperty("网络路由地址")
    private Integer station;

    @ApiModelProperty("ba网络类型")
    private Integer type;


    @ApiModelProperty("设备字段主键id")
    private String fieldId;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("字段描述")
    private String fieldDescription;

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
