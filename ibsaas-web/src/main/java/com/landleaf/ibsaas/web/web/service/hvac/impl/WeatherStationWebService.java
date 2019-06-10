package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.domain.hvac.vo.WeatherStationVO;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IWeatherStationWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/6 16:29
 * @description:
 */
@Service
@Slf4j
public class WeatherStationWebService extends BaseDeviceService implements IWeatherStationWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<WeatherStationVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(HvacConstant.WEATHER_STATION_PORT));
    }

    @Override
    public WeatherStationVO getInfoById(String id) {
        List<WeatherStationVO> weatherStationVOList = overview();
        for (WeatherStationVO ws:weatherStationVOList){
            if(ws.getId().equals(id)){
                return ws;
            }
        }
        return null;
    }
}
