package com.landleaf.ibsaas.common.domain.hvac.modbus;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/7/5 15:32
 * @description:
 */
@Data
@Table(name = "t_mb_node")
@ApiModel("有关modbus的节点")
public class MbNode extends BasicEntity {

    @ApiModelProperty("modbus设备类型")
    private Integer mbType;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("楼层")
    private Integer floor;

    @ApiModelProperty("节点描述")
    private String description;
}
