package com.landleaf.ibsaas.screen.service;

import com.landleaf.ibsaas.common.dao.energy.EnergyDataDao;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO;
import com.landleaf.ibsaas.common.utils.HlVlUtil;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.landleaf.ibsaas.screen.enums.ScreenEnergyDateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/12/13 10:53
 * @description:
 */
@Service
@Slf4j
public class ScreenEnergyService {

    @Autowired
    private ScreenRedisService screenRedisService;

    @Autowired
    private EnergyDataDao energyDataDao;

    /*
    以下为当前数值
     */

    /**
     * 每天抽取 年月日的值
     * @return
     */
    public Map<String, Object> dailyElectricValue(){
        //当前时间
        LocalDate now = LocalDate.now();
        Date firstDate = energyDataDao.getFirstDate();

        //获取当天的抄表总值
        Date firstDayTime = CalendarUtil.localDate2Date(now);
        firstDayTime = firstDayTime.before(firstDate)? firstDate: firstDayTime;
        BigDecimal sumDay = energyDataDao.getSumElectricByDate(firstDayTime);


        //获取当月的抄表总值
        Date firstMonthTime = CalendarUtil.localDate2Date(LocalDate.of(now.getYear(), now.getMonth(), 1));
        firstMonthTime = firstMonthTime.before(firstDate)? firstDate: firstMonthTime;
        BigDecimal sumMonth = energyDataDao.getSumElectricByDate(firstMonthTime);

        //获取当年的抄表总值
        Date firstYearTime = CalendarUtil.localDate2Date(LocalDate.of(now.getYear(), 1, 1));
        firstYearTime = firstYearTime.before(firstDate)? firstDate: firstYearTime;
        BigDecimal sumYear = energyDataDao.getSumElectricByDate(firstYearTime);

        return new HashMap<String, Object>(4){{
            put(ScreenEnergyDateTypeEnum.DAY.getType(), sumDay.toString());
            put(ScreenEnergyDateTypeEnum.MONTH.getType(), sumMonth.toString());
            put(ScreenEnergyDateTypeEnum.YEAR.getType(), sumYear.toString());
        }};
    }


    /**
     * 刷入redis
     */
    public void lgcSumElectric2Redis(){
        Map<String, Object> dailyElectricValue = dailyElectricValue();
        screenRedisService.setLgcSumElectricDetail(dailyElectricValue);
    }


    /**
     * 获取电表的电量值
     * @param type
     * @return
     */
    public BigDecimal getLgcSumElectricByType(String type){
        String electric = screenRedisService.getLgcSumElectricByType(type);
        if(StringUtils.isNotBlank(electric)){
            return new BigDecimal(electric);
        }
        return BigDecimal.ZERO;
    }


    /**
     * 获取当前总抄表数据
     * @return
     */
    public BigDecimal currentSumElectric(){
        List<ElectricMeterVO> electricMeter = screenRedisService.getElectricMeter();
        return electricMeter.stream()
                .map(er -> StringUtils.isNotBlank(er.getEmReading())?
                        new BigDecimal(er.getEmReading()):BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }





    /*
        以下为线型图
     */

    /**
     * 折线图
     * @return
     */
    public HlVl energyLineChart(){
        LocalDateTime now = LocalDateTime.now();
        Date endMonth = CalendarUtil.localDate2Date(LocalDate.of(now.getYear(), now.getMonth(), 1));

        Date startMonth = CalendarUtil.offsetDate(endMonth, -11, ChronoUnit.MONTHS);
        //获取月度坐标
        List<String> xs = HlVlUtil.getXs(startMonth, endMonth, 3);



        return null;
    }

}
