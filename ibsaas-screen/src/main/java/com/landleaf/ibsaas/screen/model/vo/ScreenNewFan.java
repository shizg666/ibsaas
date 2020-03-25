package com.landleaf.ibsaas.screen.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/12/11 11:18
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("大屏新风机状态数据")
public class ScreenNewFan implements Serializable {

    /**
     * 某项设备主键id
     */
    @ApiModelProperty("某项设备主键id")
    private String id;

    /**
     * 机组开关机
     * onOff
     */
    @ApiModelProperty("机组开关机")
    private String onOff;

    /**
     * 运行模式(1:夏季 2:除湿 3:冬季 4:通风 5:智能)
     * runningMode
     */
    @ApiModelProperty("运行模式(1:夏季 2:除湿 3:冬季 4:通风 5:智能)")
    private String runningMode;
}
