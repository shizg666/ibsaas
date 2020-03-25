package com.landleaf.ibsaas.screen.service;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.screen.model.dto.CityWeatherDTO;
import com.landleaf.ibsaas.screen.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/12 19:05
 * @description:
 */
@Service
public class WeatherInfoService {

    /**
     * 至慧管家天气接口 备用
     */
    private static final String SH_WEATHER_URL = "http://www.lokosmart.com:38082/web/api/weather/info/";

    /**
     * showapi信息 测试地址
     */
    private static final String SHOW_API_GPS_URL_TEST = "http://ali-weather.showapi.com/gps-to-weather";
    private static final String SHOW_API_GPS_URL = "http://weather01.market.alicloudapi.com/gps-to-weather";
    private static final String FIVE = "5";

    /**
     * 固定字符串
     */
    private static final String FROM = "from";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String AUTHORIZATION = "Authorization";
    private static final String APP_CODE_PREFIX = "APPCODE ";

    /**
     * 动态信息
     */
    @Value("${screen.weather.app-code}")
    private String appCode;
    @Value("${screen.weather.lgc-coord.lat}")
    private String lgcCoordLat;
    @Value("${screen.weather.lgc-coord.lng}")
    private String lgcCoordLng;



    @Autowired
    private ScreenRedisService screenRedisService;


    /**
     * 天气信息刷入redis
     */
    public void lgcWeather2Redis(){
        Map<String, String> paramMap = new HashMap<String, String>(){{
            put(FROM, FIVE);
            put(LAT, lgcCoordLat);
            put(LNG, lgcCoordLng);
        }};

        Map<String, String> headerMap = new HashMap<String, String>(){{
            put(AUTHORIZATION, APP_CODE_PREFIX + appCode);

        }};
        String s = RestTemplateUtil.get(SHOW_API_GPS_URL, headerMap, paramMap);
        screenRedisService.setLgcWeather(s.trim());
    }


    public JSONObject getLgcWeather(){
        return screenRedisService.getLgcWeather();
    }



























    /**
     * 存入天气信息
     * @param cityName
     */
    public void weather2Redis(String cityName){
        CityWeatherDTO c = getFromUrl(cityName);
        if(c!=null){
            screenRedisService.setWeatherByCity(cityName, c);
        }
    }


    /**
     * 获取天气信息
     * @param cityName
     * @return
     */
    public CityWeatherDTO getWeatherFromRedis(String cityName){
        return screenRedisService.getWeatherByCity(cityName);
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
