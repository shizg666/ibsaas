package com.landleaf.ibsaas.screen.service;

import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.screen.enums.ScreenHvacEnum;
import com.landleaf.ibsaas.screen.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/12 16:06
 * @description:
 */
@Service
public class ScreenFluxService {

    @Autowired
    private LargeScreenService screenService;




    public void sensor(Map<String, Object> map){
        Map<String, SensorVO> status = screenService.sensorStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }


    public void newFan(Map<String, Object> map){
        Map<String, ScreenNewFan> status = screenService.newFanStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }


    public void fanCoil(Map<String, Object> map){
        ScreenFanCoil status = screenService.fanCoilStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }



    public void achpDetail(Map<String, Object> map){
        Map<String, List<ScreenAchpDetail>> status = screenService.achpDetailStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }


    public void weather(Map<String, Object> map){
        ScreenWeather status = screenService.weatherStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }


    public void energy(Map<String, Object> map){
        ScreenEnergy status = screenService.energyStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }

    public void meeting(Map<String, Object> map){
        List<LgcMeeting> status = screenService.meetingStatus();
        map.put(ScreenHvacEnum.SENSOR.getType(), status);
    }

}
