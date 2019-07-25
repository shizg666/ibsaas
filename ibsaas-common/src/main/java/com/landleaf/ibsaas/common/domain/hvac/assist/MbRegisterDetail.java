package com.landleaf.ibsaas.common.domain.hvac.assist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lokiy
 * @date 2019/7/8 10:36
 * @description:
 */
@Data
@ApiModel("寄存器详情")
public class MbRegisterDetail {

    @ApiModelProperty("寄存器点位id")
    private String registerId;

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("类型")
    private Integer mbType;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("站点id")
    private String masterId;

    @ApiModelProperty("站点地址")
    private String host;

    @ApiModelProperty("站点端口")
    private Integer port;

    @ApiModelProperty("字段id")
    private String fieldId;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("字段描述")
    private String fieldDescription;

    @ApiModelProperty("从节点id")
    private Integer slaveId;

    @ApiModelProperty("功能码")
    private Integer functionCode;

    @ApiModelProperty("内存地址偏移量")
    private Integer offset;

    @ApiModelProperty("mb数据类型")
    private Integer dataType;
}
