package com.landleaf.ibsaas.screen.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/12/12 19:08
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("大屏天气")
public class ScreenWeather {

    /**
     * 以下数据来于接口
     */
    @ApiModelProperty(value = "图片地址",notes = "http://47.100.3.98:30002/images/new_ico/00晴.png")
    private String picUrl;

    @ApiModelProperty(value = "天气",notes = "晴")
    private String weatherStatus;

    /**
     * 以下数据来自楼层气象站
     */
    @ApiModelProperty("室外温度")
    private String wsTemp;

    @ApiModelProperty("室外湿度")
    private String wsHum;

    @ApiModelProperty("室外pm2.5")
    private String wsPm25;
}
