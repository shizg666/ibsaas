package com.landleaf.ibsaas.common.domain.energy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/18 11:00
 * @description: 报表入参对象
 */
@Data
@ApiModel("报表入参对象")
public class EnergyReportExDTO implements Serializable {


    @ApiModelProperty("时间维度 1-时 2-日 3-月 4-年")
    private Integer dateType = 1;
    @ApiModelProperty("时间段开始时间")
    private Date startTime;
    @ApiModelProperty("时间段结束时间")
    private Date endTime;
    @ApiModelProperty("能耗类型 1-水 2-电")
    private Integer equipType = 2;

    @ApiModelProperty("设备所属区域 ")
    private Integer equipArea;
    @ApiModelProperty("设备所属分类")
    private Integer equipClassification;

    private String dateTypeValue;


}
