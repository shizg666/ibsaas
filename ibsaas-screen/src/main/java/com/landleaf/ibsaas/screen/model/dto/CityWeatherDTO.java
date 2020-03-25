package com.landleaf.ibsaas.screen.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 天气信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("城市对应天气数据")
public class CityWeatherDTO implements Serializable {

    private static final long serialVersionUID = 3318051592712017608L;
    @ApiModelProperty(value = "日历",notes = "八月初四")
    private String calender;
    @ApiModelProperty(value = "城市名称",notes = "保定")
    private String cityName;
    @ApiModelProperty(value = "cityUrl",notes = "")
    private String cityUrl;
    @ApiModelProperty(value = "冷热级别",notes = "少发")
    private String coldLevel;
    @ApiModelProperty(value = "日期",notes = "2019年09月02日")
    private String date;
    @ApiModelProperty(value = "穿衣指数",notes = "热")
    private String dressLevel;
    @ApiModelProperty(value = "湿度",notes = "39")
    private String humidity;
    @ApiModelProperty(value = "newPicUrl",notes = "")
    private String newPicUrl;
    @ApiModelProperty(value = "图片地址",notes = "http://47.100.3.98:30002/images/new_ico/00晴.png")
    private String picUrl;
    @ApiModelProperty(value = "pm25",notes = "29")
    private String pm25;
    @ApiModelProperty(value = "日期",notes = "2019.09.02")
    private String singleCalender;
    @ApiModelProperty(value = "运动指数",notes = "较适宜")
    private String sportLevel;
    @ApiModelProperty(value = "温度",notes = "31")
    private String temp;
    @ApiModelProperty(value = "更新时间",notes = "2019.09.02 14:00 发布")
    private String updateTime;
    @ApiModelProperty(value = "紫外线级别",notes = "很强")
    private String uvLevel;
    @ApiModelProperty(value = "天气",notes = "晴")
    private String weatherStatus;
    @ApiModelProperty(value = "星期",notes = "星期一")
    private String week;
    @ApiModelProperty(value = "风向",notes = "西南风")
    private String windDirection;
    @ApiModelProperty(value = "风力级别",notes = "2级")
    private String windLevel;





}
