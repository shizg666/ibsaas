package com.landleaf.ibsaas.common.domain.hvac;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/5/28 8:46
 * @description: 远程设备节点
 */
@Data
@Table(name = "t_hvac_node")
@ApiModel("远程设备节点")
public class HvacNode extends BasicEntity {

    /**
     * 设备id
     */
    @ApiModelProperty("设备id")
    private String deviceId;

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
