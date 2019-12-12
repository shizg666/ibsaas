package com.landleaf.ibsaas.screen.service;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.screen.model.dto.CityWeatherDTO;
import com.landleaf.ibsaas.screen.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lokiy
 * @date 2019/12/12 19:05
 * @description:
 */
@Service
public class WeatherInfoService {

    private static final String SH_WEATHER_URL = "http://www.lokosmart.com:38082/web/api/weather/info/";

    @Autowired
    private RedisHandle redisHandle;

    private String cityWeatherPrefix = "city_weather";

    /**
     * 存入天气信息
     * @param cityName
     */
    public void weather2Redis(String cityName){
        CityWeatherDTO c = getFromUrl(cityName);
        if(c!=null){
            redisHandle.addMap(cityWeatherPrefix, cityName, c);
        }
    }


    /**
     * 获取天气信息
     * @param cityName
     * @return
     */
    public CityWeatherDTO getWeatherFromRedis(String cityName){
        return redisHandle.getMapField(cityWeatherPrefix, cityName);
    }


    /**
     * 从sh获取天气信息
     * @param cityName
     * @return
     */
    private CityWeatherDTO getFromUrl(String cityName){
        String jsonStr = RestTemplateUtil.get(SH_WEATHER_URL + cityName);
        if(StringUtils.isBlank(jsonStr)){
            return null;
        }
        try {
            Response response = JSONObject.parseObject(jsonStr, Response.class);
            JSONObject jo = (JSONObject) response.getResult();
            if(jo!=null){
               return jo.toJavaObject(CityWeatherDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }
}
