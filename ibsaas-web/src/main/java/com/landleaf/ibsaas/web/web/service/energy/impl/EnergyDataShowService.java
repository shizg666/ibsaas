package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataShowDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataShow;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.ScreenElectric;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyDataShowVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowDTO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowQryDTO;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyDataShowService;
import com.landleaf.ibsaas.web.web.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/14 17:38
 * @description:
 */
@Service
@Slf4j
public class EnergyDataShowService extends AbstractBaseService<EnergyDataShowDao, EnergyDataShow> implements IEnergyDataShowService {


    @Autowired
    private EnergyDataShowDao energyDataShowDao;
    @Autowired
    private RedisUtil redisUtil;
    public static final String ENERGY_SHOW_LIST = "energy:show:list";
    //当月能耗统计
    public static final String ENERGY_SHOW_TOTAL_MONTH = "energy:show:total:month";
    //当年能耗统计
    public static final String ENERGY_SHOW_TOTAL_YEAR = "energy:show:total:year";




    @Override
    public void addData(EnergyDataShowDTO request) {
        EnergyDataShow dataShow = new EnergyDataShow();
        BeanUtils.copyProperties(request,dataShow);
        Date date = new Date();
        dataShow.setCt(date);
        dataShow.setUt(date);
        energyDataShowDao.insert(dataShow);
        refreshEnergyDataShow();
    }

    @Override
    public void updateById(EnergyDataShowDTO request) {
        EnergyDataShow dataShow = new EnergyDataShow();
        BeanUtils.copyProperties(request,dataShow);
        Date date = new Date();
        dataShow.setUt(date);
        energyDataShowDao.updateByPrimaryKeySelective(dataShow);
        refreshEnergyDataShow();
    }

    @Override
    public PageInfo<EnergyDataShowVO> getList(EnergyDataShowQryDTO request) {
        LocalDateTime start = LocalDateTime.of(Integer.valueOf(request.getTime()),1,1,1,1,1);
        LocalDateTime end = LocalDateTime.of(Integer.valueOf(request.getTime()),12,31,1,1,1);
        long startLong = LocalDateTimeUtil.getMilliByTime(LocalDateTimeUtil.getDayStart(LocalDateTimeUtil.getMonthOfFirstDay(start)));
        long endLong = LocalDateTimeUtil.getMilliByTime(LocalDateTimeUtil.getDayStart(LocalDateTimeUtil.getMonthOfLastDay(end)));
        PageHelper.startPage(request.getPage(), request.getLimit());
        List<EnergyDataShowVO> dataShowVOS = energyDataShowDao.getListPage(startLong,endLong);
        return new PageInfo<>(dataShowVOS);
    }

    @Override
    public void refreshEnergyDataShow() {
        //获取最新的12个月的数据
        List<EnergyDataShowVO> data = energyDataShowDao.getListLatest12();
        if (CollectionUtils.isEmpty(data)){
            return;
        }
        Long latestTime = data.get(0).getTime();
        List<Integer> xpos = buildXPosList(latestTime);
        List<String> ypos = Lists.newArrayListWithCapacity(12);
        Map<Integer,String> mapdata = Maps.newHashMapWithExpectedSize(12);
        for (EnergyDataShowVO datum : data) {
            mapdata.put(LocalDateTimeUtil.long2LocalDateTime(datum.getTime()).getMonthValue(),datum.getValue().toString());
        }
        for (Integer xpo : xpos) {
            if (mapdata.containsKey(xpo)){
                ypos.add(mapdata.get(xpo));
            }else {
                ypos.add(BigDecimal.ZERO.toString());
            }
        }
        HlVl result = new HlVl();
        result.setXs(xpos);
        result.setYs(ypos);
        LocalDateTime startTime = LocalDateTimeUtil.getStartOrEndDayOfYear(LocalDate.now(),true);
        LocalDateTime endTime = LocalDateTimeUtil.getStartOrEndDayOfYear(LocalDate.now(),false);
        BigDecimal totalYear = energyDataShowDao.getCount(LocalDateTimeUtil.getMilliByTime(startTime),LocalDateTimeUtil.getMilliByTime(endTime));
        redisUtil.set(ENERGY_SHOW_TOTAL_YEAR,totalYear==null?BigDecimal.ZERO.toString():totalYear.toString());
        int nowMonth = LocalDate.now().getMonthValue();
        String monthTotal = BigDecimal.ZERO.toString();
        if (mapdata.containsKey(nowMonth)){
            monthTotal = mapdata.get(nowMonth);
        }
        redisUtil.set(ENERGY_SHOW_TOTAL_MONTH,monthTotal);
        redisUtil.set(ENERGY_SHOW_LIST, result);

    }

    private List<Integer> buildXPosList(Long latestTime) {
        List<Integer> xpos = Lists.newArrayListWithCapacity(12);
        LocalDateTime localDateTime = LocalDateTimeUtil.long2LocalDateTime(latestTime);
        for (int i =11;i>=1 ;i--){
            xpos.add(localDateTime.minusMonths(i).getMonthValue());
        }
        xpos.add(localDateTime.getMonthValue());
        return xpos;
    }
}
