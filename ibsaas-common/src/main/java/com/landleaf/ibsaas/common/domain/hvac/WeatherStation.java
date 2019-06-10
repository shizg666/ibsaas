package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 10:09
 * @description: 气象站对象
 */
@Data
@ApiModel("气象站")
public class WeatherStation extends BaseDevice implements Serializable {

    /**
     * 气象站温度
     */
    @ApiModelProperty("气象站温度")
    private String wsTemp;

    /**
     * 气象站湿度
     */
    @ApiModelProperty("气象站湿度")
    private String wsHum;

    /**
     * 气象站风速
     */
    @ApiModelProperty("气象站风速")
    private String wsWindSpeed;

    /**
     * 气象站风向
     */
    @ApiModelProperty("气象站风向")
    private String wsWindDirection;

    /**
     * 气象站降雨量
     */
    @ApiModelProperty("气象站降雨量")
    private String wsRainfall;

    /**
     * 气象站PM2.5
     */
    @ApiModelProperty("气象站PM2.5")
    private String wsPm25;

    /**
     * 气象站PM10
     */
    @ApiModelProperty("气象站PM10")
    private String wsPm10;

    /**
     * 气象站CO2
     */
    @ApiModelProperty("气象站CO2")
    private String wsCo2;

    /**
     * 气象站噪音
     */
    @ApiModelProperty("气象站噪音")
    private String wsNoise;
}
