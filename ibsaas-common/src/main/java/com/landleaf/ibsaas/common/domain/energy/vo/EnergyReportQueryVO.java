package com.landleaf.ibsaas.common.domain.energy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("能源报表查询对象")
public class EnergyReportQueryVO implements Serializable {


    @ApiModelProperty("时间维度 1-时 2-日 3-月 4-年")
    private Integer dateType;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("能耗类型 1-水 2-电")
    private Integer energyType;
    @ApiModelProperty("查询维度 1-分区 2-分项 ")
    private Integer queryType;
    @ApiModelProperty("查询值")
    private Integer queryValue;

    //时间维度 code 跟数据库的字段一致方便拼接sql "energy_data_"+dateCode
    private String dateCode;


    public EnergyReportQueryVO(Integer dateType, String startTime, String endTime, Integer energyType, Integer queryType, Integer queryValue) {
        this.dateType = dateType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.energyType = energyType;
        this.queryType = queryType;
        this.queryValue = queryValue;
    }
}
