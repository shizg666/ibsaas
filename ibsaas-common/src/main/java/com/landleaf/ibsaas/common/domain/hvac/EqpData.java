package com.landleaf.ibsaas.common.domain.hvac;

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
 * @date 2019/7/29 17:23
 * @description:
 */
@Data
@Table(name = "t_eqp_data")
@ApiModel("设备数据表")
public class EqpData extends BasicEntity implements Serializable {

    @ApiModelProperty("协议类型 1-bacnet 2-modbus 3-r232")
    private Integer protocolType;

    @ApiModelProperty("设备类型")
    private Integer deviceType;

    @ApiModelProperty("对应的点位id")
    private String pointId;

    @ApiModelProperty("数据记录时间")
    private Date eqpDataTime;

    @ApiModelProperty("数据记录日期")
    private Date eqpDataDate;

    @ApiModelProperty("数据记录月份")
    private String eqpDataMonth;

    @ApiModelProperty("数据记录年份")
    private Integer eqpDataYear;

    @ApiModelProperty("当前数据值")
    private BigDecimal eqpDataValue;

    @ApiModelProperty("数据来源 1-1小时记录数值")
    private Integer eqpDataSource;

}
