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
 * @date 2019/6/12 13:39
 * @description:
 */
@Data
@Table(name = "t_energy_equip_data")
@ApiModel("能源设备数据记录")
public class EnergyEquipData extends BasicEntity {

    @ApiModelProperty("设备id")
    private String equipId;

    @ApiModelProperty("数据记录时间")
    private Date dataTime;

    @ApiModelProperty("数据记录值")
    private BigDecimal dataValue;

    @ApiModelProperty("数据记录增长值")
    private BigDecimal dataIncreaseValue;

    @ApiModelProperty("间隔类型")
    private Integer dataType;
}
