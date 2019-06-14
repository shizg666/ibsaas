package com.landleaf.ibsaas.common.domain.energy;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/14 15:10
 * @description:
 */
@Data
@Table(name = "t_energy_data_electric")
@ApiModel("电表抄表数据")
public class EnergyDataElectric extends BasicEntity implements Serializable {


    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("电表抄表时间")
    private Date electricDataTime;

    @ApiModelProperty("电表抄表值")
    private BigDecimal electricDataValue;

    @ApiModelProperty("电表抄表增长值")
    private BigDecimal electricDataIncreaseValue;

}
