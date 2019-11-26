package com.landleaf.ibsaas.common.domain.hvac.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/11/26 0026
 * @description:
 */
@Data
@ApiModel("远程设备节点")
public class MbNodeVO implements Serializable {

    /**
     * 远程设备节点id
     */
    @ApiModelProperty("远程设备节点id")
    private String id;
    private String nodeId;

    @ApiModelProperty("设备类型")
    private Integer mbType;

    /**
     * 设备id
     */
    @ApiModelProperty("设备类型")
    private Integer deviceType;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;

    /**
     * 设备节点所在楼层
     */
    @ApiModelProperty("设备节点所在楼层")
    private Integer floor;

}
