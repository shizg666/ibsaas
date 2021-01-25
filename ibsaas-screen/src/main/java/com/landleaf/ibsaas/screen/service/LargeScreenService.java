package com.landleaf.ibsaas.screen.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.ScreenElectric;
import com.landleaf.ibsaas.common.domain.hvac.vo.*;
import com.landleaf.ibsaas.common.enums.hvac.sensor.SensorHchoLevelEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.date.LocalAndDateUtil;
import com.landleaf.ibsaas.common.utils.number.NumberUtils;
import com.landleaf.ibsaas.screen.enums.ScreenEnergyDateTypeEnum;
import com.landleaf.ibsaas.screen.enums.ScreenNewFanEnum;
import com.landleaf.ibsaas.screen.enums.ScreenSensorEnum;
import com.landleaf.ibsaas.screen.model.vo.*;
import com.landleaf.ibsaas.screen.util.ScreenValueUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private ScreenEnergyService screenEnergyService;

    @Autowired
    private WeatherInfoService weatherInfoService;

    @Autowired
    private RedisHandle redisHandle;

    public static final String ENERGY_SHOW_LIST = "energy:show:list";
    //当月能耗统计
    public static final String ENERGY_SHOW_TOTAL_MONTH = "energy:show:total:month";
    //当年能耗统计
    public static final String ENERGY_SHOW_TOTAL_YEAR = "energy:show:total:year";

    /**
     * 获取大屏多参数据
     * @return
     */
    public Map<String, SensorVO> sensorStatus(){
        //楼层 + 传感器数据  (取电梯口传感器数据)
        Map<String, SensorVO> result = Maps.newHashMap();

        List<SensorVO> sensors = redisService.getSensors();
        Map<String, SensorVO> tempMap = sensors.stream().collect(Collectors.toMap(SensorVO::getId, s -> s));
        //填充楼层和传感器数据
        for(ScreenSensorEnum e:ScreenSensorEnum.values()){
            SensorVO sensorVO = tempMap.get(e.getNodeId());
            if(sensorVO == null){
                sensorVO = defaultSensorVO(e.getNodeId());
            }

            sensorVO.setSsTemp(
                    ScreenValueUtil.changeVal(
                        ScreenValueUtil.retainDecimals(sensorVO.getSsTemp(), 1),
                    "1"));
            sensorVO.setSsHum(ScreenValueUtil.retainDecimals(sensorVO.getSsHum(), 2));

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
        Map<String, ScreenNewFan> result = Maps.newHashMap();

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


    private static final String KEY_1 = "1";
    private static final String KEY_2 = "2";

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
     * 风冷热泵状态数据
     * @return
     */
    public Map<String, List<ScreenAchpDetail>> achpDetailStatus(){
        Map<String, List<ScreenAchpDetail>> result = new HashMap<String, List<ScreenAchpDetail>>(4){{
            put(KEY_1, new ArrayList<>());
            put(KEY_2, new ArrayList<>());
        }};
        List<AchpDetailVO> achpDetails = redisService.getAchpDetail();
        for (int i = 0; i < 6; i++) {
            AchpDetailVO achpDetailVO = achpDetails.get(i);
            ScreenAchpDetail temp;
            if(achpDetailVO!=null){
                temp = new ScreenAchpDetail();
                temp.setAdOnOffState(achpDetailVO.getAdOnOffState());
            }else {
                temp = defaultScreenAchpDetail();
            }
            if(i < 3){
                //机组一低区风冷热泵
                result.get(KEY_1).add(temp);
            }else {
                //机组二高区风冷热泵
                result.get(KEY_2).add(temp);
            }
        }
        return result;
    }


    /**
     * 获取会议室列表
     * @return
     */
    public List<LgcMeeting> meetingStatus(){
        String day = LocalAndDateUtil.date2StrPattern(new Date(), LocalAndDateUtil.YYYY_MM_DD);
        return redisService.getMeetingList(day);
    }


    /**
     * 能耗状态数据
     * @return
     */
    public ScreenEnergy energyStatus(){
        ScreenEnergy result = new ScreenEnergy();
        ScreenElectric electricNumeric = getElectricNumeric();
        HlVl electricGraphics = getElectricGraphics(electricNumeric);
        result.setElectricNumeric(electricNumeric);
        result.setElectricGraphics(electricGraphics);
        return result;
    }

    /**
     * 能耗状态数据
     * @return
     */
    public ScreenEnergy energyStatus2(){
        ScreenEnergy result = new ScreenEnergy();
        ScreenElectric electricNumeric = new ScreenElectric();
        String month = (String) redisHandle.get(ENERGY_SHOW_TOTAL_MONTH);
        String year = (String) redisHandle.get(ENERGY_SHOW_TOTAL_YEAR);
        electricNumeric.setYearTotal(year);
        electricNumeric.setMonthTotal(month);
        HlVl data = (HlVl) redisHandle.get(ENERGY_SHOW_LIST);
        result.setElectricGraphics(data);
        result.setElectricNumeric(electricNumeric);
        return result;
    }


    /**
     * 获取数值类型的电表数据
     * @return
     */
    public ScreenElectric getElectricNumeric(){
        ScreenElectric result = new ScreenElectric();
        BigDecimal currentSum = screenEnergyService.currentSumElectric();
        BigDecimal monthSum = screenEnergyService.getLgcSumElectricByType(ScreenEnergyDateTypeEnum.MONTH.getType());
        BigDecimal yearSum = screenEnergyService.getLgcSumElectricByType(ScreenEnergyDateTypeEnum.YEAR.getType());


        result.setYearTotal(currentSum.subtract(yearSum)
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .toString());
        result.setMonthTotal(currentSum.subtract(monthSum)
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .toString());
        return result;
    }

    /**
     * 获取图形的电表数据
     * @param screenElectric
     * @return
     */
    public HlVl getElectricGraphics(ScreenElectric screenElectric){
        HlVl hlVl = screenEnergyService.getLgcElectricLineChart();
        List<String> tempXs = ((List<String>) hlVl.getXs()).stream().map(s -> s.substring(s.length() - 2)).collect(Collectors.toList());
        hlVl.setXs(tempXs);
        List<String> ys = (List<String>) hlVl.getYs();
        ys.set(ys.size()-1, screenElectric.getMonthTotal());
        return hlVl;
    }





    /**
     * 获取天气信息
     * @return
     */
    public ScreenWeather weatherStatus(){
        //返回对象
        ScreenWeather result = new ScreenWeather();
        //地区天气
        JSONObject lgcWeather = weatherInfoService.getLgcWeather();
        //大楼气象站数据
        WeatherStationVO ws = redisService.getWeatherStation();

        result.setWeatherStatus(lgcWeather.getJSONObject("showapi_res_body").getJSONObject("now").getString("weather"));
        result.setPicUrl(lgcWeather.getJSONObject("showapi_res_body").getJSONObject("now").getString("weather_pic"));
        result.setWsTemp(ScreenValueUtil.retainDecimals(ws.getWsTemp(), 1));
        try {
            String wsTemp = ScreenValueUtil.retainDecimals(ws.getWsTemp(), 1);
            if(NumberUtils.isNumber(wsTemp)&&Double.parseDouble(wsTemp)>100){
               result.setWsTemp(lgcWeather.getJSONObject("showapi_res_body").getJSONObject("now").getString("temperature"));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        result.setWsHum(ScreenValueUtil.retainDecimals(ws.getWsHum(), 2));
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
        sensor.setSsTemp("20.2");
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

    /**
     * 默认风冷热泵状态
     * @return
     */
    private ScreenAchpDetail defaultScreenAchpDetail(){
        ScreenAchpDetail screenAchpDetail = new ScreenAchpDetail();
        screenAchpDetail.setAdOnOffState("0");
        return screenAchpDetail;
    }


    private LgcMeeting defaultLgcMeeting(){
        LgcMeeting lgcMeeting = new LgcMeeting();
        lgcMeeting.setMeetingTime("10-01 13:00~18:00");
        lgcMeeting.setMeetingRoom("SH 2F会议室四姑娘二峰");
        lgcMeeting.setContent("Ibsaas项目技术研讨会议");
        return lgcMeeting;
    }
}
