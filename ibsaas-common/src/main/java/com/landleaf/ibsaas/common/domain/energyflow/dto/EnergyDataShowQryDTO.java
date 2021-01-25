package com.landleaf.ibsaas.common.domain.energyflow.dto;

import com.landleaf.ibsaas.common.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/5/28 8:54
 * @description:
 */
@ApiModel("前端能耗数据展示")
@Data
public class EnergyDataShowQryDTO  extends BaseDTO implements Serializable {

    @ApiModelProperty("时间 年")
    private String time;
}
