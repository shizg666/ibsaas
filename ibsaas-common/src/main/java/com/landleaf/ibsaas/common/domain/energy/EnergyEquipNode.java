package com.landleaf.ibsaas.common.domain.energy;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/6/12 13:52
 * @description:
 */
@Data
@Table(name = "t_energy_equip_node")
@ApiModel("设备节点关系")
public class EnergyEquipNode extends BasicEntity {

    @ApiModelProperty("设备id")
    private String equipId;

    @ApiModelProperty("节点id")
    private String nodeId;
}
