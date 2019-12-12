package com.landleaf.ibsaas.screen.service;

import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.WeatherStationVO;
import com.landleaf.ibsaas.common.enums.hvac.sensor.SensorHchoLevelEnum;
import com.landleaf.ibsaas.screen.enums.ScreenNewFanEnum;
import com.landleaf.ibsaas.screen.enums.ScreenSensorEnum;
import com.landleaf.ibsaas.screen.model.dto.CityWeatherDTO;
import com.landleaf.ibsaas.screen.model.vo.LgcMeeting;
import com.landleaf.ibsaas.screen.model.vo.ScreenFanCoil;
import com.landleaf.ibsaas.screen.model.vo.ScreenNewFan;
import com.landleaf.ibsaas.screen.model.vo.ScreenWeather;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/12/11 9:23
 * @description:
 */
@Service
public class LargeScreenService {

    @Autowired
    private ScreenRedisService redisService;

    @Autowired
    private WeatherInfoService weatherInfoService;
    /**
     * 获取大屏多参数据
     * @return
     */
    public Map<String, SensorVO> sensorStatus(){
        //楼层 + 传感器数据  (取电梯口传感器数据)
        Map<String, SensorVO> result = new HashMap<>(8);

        List<SensorVO> sensors = redisService.getSensors();
        Map<String, SensorVO> tempMap = sensors.stream().collect(Collectors.toMap(SensorVO::getId, s -> s));
        //填充楼层和传感器数据
        for(ScreenSensorEnum e:ScreenSensorEnum.values()){
            SensorVO sensorVO = tempMap.get(e.getNodeId());
            if(sensorVO == null){
                sensorVO = defaultSensorVO(e.getNodeId());
            }
            sensorVO.setSsHchoLevel(SensorHchoLevelEnum.getLevel(sensorVO.getSsHcho()));
            result.put(e.getFloor(), sensorVO);
        }
        return result;
    }


    /**
     * 新风机状态数据
     * @return
     */
    public Map<String, ScreenNewFan> newFanStatus(){
        //机组数 + 机组数据
        Map<String, ScreenNewFan> result = new HashMap<>(8);

        List<NewFanVO> newFans = redisService.getNewFans();
        Map<String, NewFanVO> tempMap = newFans.stream().collect(Collectors.toMap(NewFanVO::getId, n -> n));
        for(ScreenNewFanEnum e:ScreenNewFanEnum.values()){
            NewFanVO newFanVO = tempMap.get(e.getNodeId());
            ScreenNewFan cnf;
            if(newFanVO == null){
                cnf = defaultNewFanVO(e.getNodeId());
            }else {
                cnf = new ScreenNewFan();
                BeanUtils.copyProperties(newFanVO, cnf);
            }
            result.put(e.getUnitNum(), cnf);
        }

        return result;
    }


    /**
     * 风盘状态数据
     * @return
     */
    public ScreenFanCoil fanCoilStatus(){
        ScreenFanCoil result = new ScreenFanCoil();
        List<FanCoilVO> fanCoils = redisService.getFanCoils();
        result.setTotalNum(String.valueOf(fanCoils.size()));
        long count = fanCoils.stream().filter(fc -> "1".equals(fc.getFcOnOff())).count();
        result.setOnNum(String.valueOf(count));
        return result;
    }


    /**
     * 获取会议室列表
     * @return
     */
    public List<LgcMeeting> meetingStatus(){
        List<LgcMeeting> meetings = new ArrayList<>();
        meetings.add(defaultLgcMeeting());
        return meetings;
    }


    /**
     * 能耗状态数据
     * @return
     */
    public Object energyStatus(){

        return null;
    }

    /**
     * 获取天气信息
     * @return
     */
    public ScreenWeather weatherStatus(){
        //返回对象
        ScreenWeather result = new ScreenWeather();
        //地区天气
        CityWeatherDTO shanghai = weatherInfoService.getWeatherFromRedis("上海");
        //大楼气象站数据
        WeatherStationVO ws = redisService.getWeatherStation();

        result.setWeatherStatus(shanghai.getWeatherStatus());
        result.setPicUrl(shanghai.getPicUrl());
        result.setWsTemp(ws.getWsTemp());
        result.setWsHum(ws.getWsHum());
        result.setWsPm25(ws.getWsPm25());

        return result;
    }


    /**
     * 默认传感器参数
     * @return
     */
    private SensorVO defaultSensorVO(String id){
        SensorVO sensor = new SensorVO();
        sensor.setId(id);
        sensor.setSsTemp("20.16");
        sensor.setSsHum("44.36");
        sensor.setSsCo2("524.0");
        sensor.setSsVoc("0.242");
        sensor.setSsPm25("121.0");
        sensor.setSsHcho("0.01");
        return sensor;
    }

    /**
     * 默认四效新风数据
     * @param id
     * @return
     */
    private ScreenNewFan defaultNewFanVO(String id){
        ScreenNewFan newFan = new ScreenNewFan();
        newFan.setId(id);
        newFan.setOnOff("0");
        newFan.setRunningMode("5");
        return newFan;
    }


    private LgcMeeting defaultLgcMeeting(){
        LgcMeeting lgcMeeting = new LgcMeeting();
        lgcMeeting.setMeetingTime(new Date());
        lgcMeeting.setMeetingRoom("SH 2F会议室四姑娘二峰");
        lgcMeeting.setContent("Ibsaas项目技术研讨会议");
        return lgcMeeting;
    }
}
