package com.landleaf.ibsaas.common.domain.energy.vo;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
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
public class EnergyEquipVO extends EnergyEquip implements Serializable {


    @ApiModelProperty("校验时间")
    private Date verifyTime;

    @ApiModelProperty("校验值")
    private BigDecimal verifyValue;

    @ApiModelProperty("绑定的节点")
    private HvacNodeVO node;

}
