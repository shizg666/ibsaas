package com.landleaf.ibsaas.common.domain.energy;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "t_energy_data_show")
@ApiModel("前端展示页面能耗数据")
public class EnergyDataShow implements Serializable {


    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private Long time;

    private BigDecimal value;

    private Date ct;

    private Date ut;

}
