package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 17:05
 * @description:
 */
@Data
@ApiModel("传感器")
public class Sensor extends BaseDevice implements Serializable {

    /**
     * 传感器温度
     */
    @ApiModelProperty("传感器温度")
    private String ssTemp;

    /**
     * 传感器湿度
     */
    @ApiModelProperty("传感器湿度")
    private String ssHum;

    /**
     * 传感器CO2
     */
    @ApiModelProperty("传感器CO2")
    private String ssCo2;

    /**
     * 传感器甲醛
     */
    @ApiModelProperty("传感器VOC")
    private String ssVoc;

    /**
     * 传感器PM2.5
     */
    @ApiModelProperty("传感器PM2.5")
    private String ssPm25;


    @ApiModelProperty("传感器甲醛")
    private String ssHcho;
}
