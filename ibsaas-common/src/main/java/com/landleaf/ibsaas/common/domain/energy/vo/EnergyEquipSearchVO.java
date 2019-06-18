package com.landleaf.ibsaas.common.domain.energy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/14 13:59
 * @description:
 */
@Data
@ApiModel("能耗基础设备查询结果对象")
public class EnergyEquipSearchVO implements Serializable {

    @ApiModelProperty("设备id")
    private String id;

    @ApiModelProperty("设备名称")
    private String equipName;

    @ApiModelProperty("设备ID")
    private String equipNo;

    @ApiModelProperty("设备类型")
    private String equipType;
    @ApiModelProperty("设备类型")
    private String equipTypeStr;

    @ApiModelProperty("设备所属楼层")
    private String equipFloor;
    @ApiModelProperty("设备所属楼层")
    private String equipFloorStr;

    @ApiModelProperty("设备所属区域")
    private String equipArea;
    @ApiModelProperty("设备所属区域")
    private String equipAreaStr;

    @ApiModelProperty("设备所属分类")
    private String equipClassification;
    @ApiModelProperty("设备所属分类")
    private String equipClassificationStr;

    @ApiModelProperty("初始化校验时间")
    private Date verifyTime;

    @ApiModelProperty("初始化校验值")
    private BigDecimal verifyValue;


    /**
     * 以下为抄表数据
     */

    @ApiModelProperty("抄表时间")
    private Date dataTime;
    @ApiModelProperty("抄表数值")
    private BigDecimal dataValue;

}
