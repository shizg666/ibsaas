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
@Table(name = "t_energy_data")
@ApiModel("水电表数据")
public class EnergyData extends BasicEntity implements Serializable {


    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("表数据时间")
    private Date energyDataTime;

    @ApiModelProperty("表数据日期")
    private Date energyDataDate;

    @ApiModelProperty("表数据月份")
    private Integer energyDataMonth;

    @ApiModelProperty("表数据年份")
    private Integer energyDataYear;

    @ApiModelProperty("水电表值")
    private BigDecimal energyDataValue;

    @ApiModelProperty("水电表增长值")
    private BigDecimal energyDataIncreaseValue;

    @ApiModelProperty("水电表数据类型 1-水 2-电")
    private Integer energyDataType;

    @ApiModelProperty("数据来源 1-1小时定时任务 2-手动刷取")
    private Integer energyDataSource;

}
