package com.landleaf.ibsaas.common.domain.energy;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/12 11:42
 * @description:
 */
@Data
@Table(name = "t_energy_equip")
@ApiModel("能耗设备")
public class EnergyEquip extends BasicEntity implements Serializable {

    @ApiModelProperty("设备对应的水电表节点id")
    private String nodeId;

    @ApiModelProperty("设备编号")
    private String equipNo;

    @ApiModelProperty("设备名称")
    @NotBlank(message = "设备名称不能为空")
    private String equipName;

    @ApiModelProperty("设备类型")
    @NotBlank(message = "设备类型不能为空")
    private String equipType;

    @ApiModelProperty("设备楼层")
    @NotBlank(message = "设备楼层不能为空")
    private String equipFloor;

    @ApiModelProperty("所属区域")
    @NotBlank(message = "所属区域不能为空")
    private String equipArea;

    @ApiModelProperty("所属分类")
    @NotBlank(message = "所属分类不能为空")
    private String equipClassification;

    @ApiModelProperty("备注")
    private String comment;
}
