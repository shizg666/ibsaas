package com.landleaf.ibsaas.common.domain.energy.dto;

import com.landleaf.ibsaas.common.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/14 13:59
 * @description:
 */
@Data
@ApiModel("能耗基础设备查询对象")
public class EnergyEquipSearchDTO  extends BaseDTO implements Serializable {

    @ApiModelProperty("设备类型")
    private String equipType;

    @ApiModelProperty("设备名称")
    private String equipName;

    @ApiModelProperty("设备所属分类")
    private String equipClassification;

    @ApiModelProperty("设备所属楼层")
    private String equipFloor;

    @ApiModelProperty("设备所属区域")
    private String equipArea;

}
