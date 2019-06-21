package com.landleaf.ibsaas.web.web.schedule;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.common.enums.energy.EnergyTypeEnum;
import com.landleaf.ibsaas.common.enums.energy.QueryTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.web.web.cache.redis.constant.RedisConstants;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 水电表数据按月保存
 * 每月一号00:15分跑
 */
@Component
@EnableScheduling
@Slf4j
public class EnergyDataToRedisSchedule {

    @Autowired
    private IEnergyReportService iEnergyReportService;
    @Autowired
    private RedisHandle redisHandle;

    @Scheduled(cron = "0 15 0 1 * ? ")
    @Async("energyDataToRedisThreadPool")
    public void energyDatatoRedis(){
        Date now = new Date();
        log.info("电表水表数据定时缓存开始------------------------------>时间为:{},ThreadName:", now,Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String gtimelast = sdf.format(c.getTime()); //上月
        System.out.println(gtimelast);
        int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(lastMonthMaxDay);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
        //按格式输出
        String endTime = sdf.format(c.getTime()); //上月最后一天
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");
        String startTime = sdf2.format(c.getTime()); //上月第一天
        log.info("电表水表数据定时缓存开始时间：{},结束时间为: {}:", startTime,endTime);

        EnergyReportQueryVO queryVO = new EnergyReportQueryVO();
        queryVO.setStartTime(startTime);
        queryVO.setEndTime(endTime);
        queryVO.setDateType(DimensionTypeEnum.MONTH.type);
        queryVO.setQueryType(QueryTypeEnum.TYPE.getType());
        queryVO.setEnergyType(EnergyTypeEnum.ENERGY_WATER.getEnergyType());
        //水 分项
        List<EnergyReportResponseVO> energyWaterVOSType =  iEnergyReportService.getEnergyReporyInfolist(queryVO);
        //水 分区
        queryVO.setQueryType(QueryTypeEnum.AREA.getType());
        List<EnergyReportResponseVO> energyWaterVOSArea =  iEnergyReportService.getEnergyReporyInfolist(queryVO);
        //电 分区
        queryVO.setEnergyType(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType());
        List<EnergyReportResponseVO> energyElVOSArea =  iEnergyReportService.getEnergyReporyInfolist(queryVO);
        //电 分项
        queryVO.setQueryType(QueryTypeEnum.TYPE.getType());
        List<EnergyReportResponseVO> energyElVOSType =  iEnergyReportService.getEnergyReporyInfolist(queryVO);


        Map<String,Map<String,List<EnergyReportResponseVO>>> dataTypeW = Maps.newHashMap();
        Map<String,Map<String,List<EnergyReportResponseVO>>> dataAreaW = Maps.newHashMap();
        Map<String,Map<String,List<EnergyReportResponseVO>>> dataTypeE = Maps.newHashMap();
        Map<String,Map<String,List<EnergyReportResponseVO>>> dataAreaE = Maps.newHashMap();

        Map<String,List<EnergyReportResponseVO>> mapWaterType = energyWaterVOSType.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        Map<String,List<EnergyReportResponseVO>> mapWaterArea = energyWaterVOSArea.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        Map<String,List<EnergyReportResponseVO>> mapElType = energyElVOSType.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        Map<String,List<EnergyReportResponseVO>> mapElArea = energyElVOSArea.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        mapWaterType.forEach((key,value)->{
            Map<String,List<EnergyReportResponseVO>> map2 = value.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
            dataTypeW.put(key,map2);
        });
        mapWaterArea.forEach((key,value)->{
            Map<String,List<EnergyReportResponseVO>> map2 = value.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
            dataAreaW.put(key,map2);
        });
        mapElType.forEach((key,value)->{
            Map<String,List<EnergyReportResponseVO>> map2 = value.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
            dataTypeE.put(key,map2);
        });
        mapElArea.forEach((key,value)->{
            Map<String,List<EnergyReportResponseVO>> map2 = value.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
            dataAreaE.put(key,map2);
        });
        //水分项
        redisHandle.addMapList(RedisConstants.ENERGY_WATER_DATA_TYPE,dataTypeW);
        //水分区
        redisHandle.addMapList(RedisConstants.ENERGY_WATER_DATA_AREA,dataAreaW);
        //电分区
        redisHandle.addMapList(RedisConstants.ENERGY_ELECTRICITY_DATA_AREA,dataAreaE);
        //电分项
        redisHandle.addMapList(RedisConstants.ENERGY_ELECTRICITY_DATA_TYPE,dataTypeE);
        Long end = System.currentTimeMillis();
        long t = end - start;
        log.info("定时任务数据保存结束------------------------------>时间为{}毫秒", t);

    }

}
