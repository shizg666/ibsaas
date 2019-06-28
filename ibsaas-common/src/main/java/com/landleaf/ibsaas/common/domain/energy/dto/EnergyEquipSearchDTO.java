package com.landleaf.ibsaas.common.domain.energy.dto;

import com.landleaf.ibsaas.common.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/14 13:59
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("能耗基础设备查询对象")
public class EnergyEquipSearchDTO  extends BaseDTO implements Serializable {

    @ApiModelProperty("设备类型")
    private String equipType;

    @ApiModelProperty("设备名称")
    private String equipName;

    @ApiModelProperty("设备所属分类")
    private String equipClassification;

    @ApiModelProperty("设备所属楼层")
    private String equipFloor;

    @ApiModelProperty("设备所属区域")
    private String equipArea;

    /**
     * 抄表数据属性
     */
    @ApiModelProperty("抄表时间")
    private Date energyDataTime;


    @ApiModelProperty("抄表开始时间")
    private Date startTime;

    @ApiModelProperty("抄表结束时间")
    private Date endTime;
}
