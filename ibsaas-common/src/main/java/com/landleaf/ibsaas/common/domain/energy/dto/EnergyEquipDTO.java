package com.landleaf.ibsaas.common.domain.energy.dto;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 17:58
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("能耗设备")
public class EnergyEquipDTO extends EnergyEquip implements Serializable {

    @ApiModelProperty("校验时间")
    private Date verifyTime;

    @ApiModelProperty("校验值")
    private BigDecimal verifyValue;

    @ApiModelProperty("校验备注")
    private String verifyComment;

}
