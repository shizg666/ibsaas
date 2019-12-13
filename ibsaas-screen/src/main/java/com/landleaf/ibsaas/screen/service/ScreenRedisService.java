package com.landleaf.ibsaas.screen.service;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.hvac.vo.*;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.screen.model.dto.CityWeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/12/11 9:25
 * @description:
 */
@Service
public class ScreenRedisService {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;

    private String cityWeatherPrefix = "city_weather";

    private String lgcWeatherPrefix = "lgc_weather";
    /*
    以下为lgc设备信息数据
     */

    /**
     * 获取多参数的数据
     * @return
     */
    public List<SensorVO> getSensors(){
        Object result = get(ModbusDeviceTypeEnum.SENSOR.getDeviceType());
        return result == null ? new ArrayList<>() : (List<SensorVO>) result;
    }


    /**
     * 获取风机盘管数据
     * @return
     */
    public List<FanCoilVO> getFanCoils(){
        Object result = get(BacnetDeviceTypeEnum.FAN_COIL.getDeviceType());
        return result == null ? new ArrayList<>() : (List<FanCoilVO>) result;
    }


    /**
     * 获取新风机数据
     * @return
     */
    public List<NewFanVO> getNewFans(){
        Object result = get(BacnetDeviceTypeEnum.NEW_FAN.getDeviceType());
        return result == null ? new ArrayList<>() : (List<NewFanVO>) result;
    }

    /**
     * 获取风冷热泵数据
     * @return
     */
    public List<AchpDetailVO> getAchpDetail() {
        Object result = get(BacnetDeviceTypeEnum.ACHP_DETAIL.getDeviceType());
        return result == null ? new ArrayList<>() : (List<AchpDetailVO>) result;
    }


    /**
     * 获取气象站信息
     * @return
     */
    public WeatherStationVO getWeatherStation(){
        Object result = get(BacnetDeviceTypeEnum.WEATHER_STATION.getDeviceType());
        return result == null ? null: ((List<WeatherStationVO>) result).get(0);
    }


    /**
     * 获取电表数据
     * @return
     */
    public List<ElectricMeterVO> getElectricMeter(){
        Object result = get(ModbusDeviceTypeEnum.ELECTRIC_METER.getDeviceType());
        return result == null ? null: (List<ElectricMeterVO>) result;
    }

    /**
     * 处理redis失败的情况
     * @param type
     * @return
     */
    private Object get(Integer type){
        try {
            return redisHandle.getMapField(placeId, String.valueOf(type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    /*
    以下为天气存储数据
     */

    /**
     * 存入天气信息
     * @param cityName
     * @param c
     */
    public void setWeatherByCity(String cityName, CityWeatherDTO c) {
        redisHandle.addMap(cityWeatherPrefix, cityName, c);
    }

    /**
     * 获取天气信息
     * @param cityName
     * @return
     */
    public CityWeatherDTO getWeatherByCity(String cityName) {
        return redisHandle.getMapField(cityWeatherPrefix, cityName);
    }


    /**
     * 存放lgc天气json格式
     * @param jsonWeather
     */
    public void setLgcWeather(String jsonWeather){
        redisHandle.set(lgcWeatherPrefix, jsonWeather);
    }

    /**
     * 获取lgcJson格式
     * @return
     */
    public JSONObject getLgcWeather(){
        String lgcJsonWeather = (String) redisHandle.get(lgcWeatherPrefix);
        return JSONObject.parseObject(lgcJsonWeather);
    }

    /*
     以下为大屏电量操作信息
     */

}
