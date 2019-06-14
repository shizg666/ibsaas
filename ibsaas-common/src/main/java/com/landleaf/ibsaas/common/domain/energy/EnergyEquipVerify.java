package com.landleaf.ibsaas.common.domain.energy;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/12 13:57
 * @description:
 */
@Data
@Table(name = "t_energy_equip_verify")
@ApiModel("能源设备校验")
public class EnergyEquipVerify extends BasicEntity {

    @ApiModelProperty("设备id")
    private String equipId;

    @ApiModelProperty("校验时间")
    private Date verifyTime;

    @ApiModelProperty("校验值")
    private BigDecimal verifyValue;

    @ApiModelProperty("校验备注")
    private String verifyComment;

    @ApiModelProperty("当前初始化启用状态")
    private Integer enableFlag;
}
