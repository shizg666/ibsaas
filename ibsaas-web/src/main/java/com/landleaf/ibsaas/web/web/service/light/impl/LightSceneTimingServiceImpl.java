package com.landleaf.ibsaas.web.web.service.light.impl;

import com.landleaf.ibsaas.common.dao.light.LightSceneTimingDao;
import com.landleaf.ibsaas.common.domain.light.LightSceneTiming;
import com.landleaf.ibsaas.common.domain.light.SceneTimingDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingReqVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingRespVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightTimingSwitchReqVO;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.cache.redis.constant.RedisConstants;
import com.landleaf.ibsaas.web.web.service.light.ILightSceneTimingService;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.IVacationSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shizg
 * @since 2019-11-21
 */
@Service
public class LightSceneTimingServiceImpl extends AbstractBaseService<LightSceneTimingDao, LightSceneTiming> implements ILightSceneTimingService<LightSceneTiming> {

    @Autowired
    private LightSceneTimingDao lightSceneTimingDao;
    @Autowired
    private IVacationSettingService iVacationSettingService;
    @Autowired
    private ILightService iLightService;
    @Autowired
    private RedisHandle redisHandle;

    @Override
    public void addAreaTime(LightSceneTimingReqVO reqVO) {
        LightSceneTiming lightSceneTiming = new LightSceneTiming();
        BeanUtils.copyProperties(reqVO,lightSceneTiming);
        lightSceneTiming.setCt(now());
        lightSceneTimingDao.insert(lightSceneTiming);
    }

    @Override
    public void timeSwitch(LightTimingSwitchReqVO reqVO) {
        LightSceneTiming lightSceneTiming = new LightSceneTiming();
        BeanUtils.copyProperties(reqVO,lightSceneTiming);
        lightSceneTiming.setUt(now());
        lightSceneTimingDao.updateByPrimaryKey(lightSceneTiming);
    }

    @Override
    public List<LightSceneTimingRespVO> getListAreaTime(Long areaId) {
        List<LightSceneTimingRespVO> data = lightSceneTimingDao.getListAreaTime(areaId);
        return data;
    }

    @Override
    @Async("energyDataToRedisThreadPool")
    public void executeTime(LocalDateTime date) {

        String day1 = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //当天日期标识  正常日期-0  节假日（包括周末（法定节假日）-1 补班日2
        String key = RedisConstants.DAY_INFO.concat(day1);
        Integer specialFlag = (Integer) redisHandle.get(key);
        if (specialFlag == null){
            specialFlag = iVacationSettingService.getSpecialFlag(date);
            redisHandle.set(key,specialFlag,86400L);
        }
//        Integer specialFlag = iVacationSettingService.getSpecialFlag(date);
        if (specialFlag == 1){
            return;
        }
        //查询出当前时间 所有启用的场景定时

        String day = date.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        List<SceneTimingDTO> data = lightSceneTimingDao.getListExecuteByTime(day);
        if(CollectionUtils.isEmpty(data)){
            return;
        }
        Integer finalSpecialFlag = specialFlag;
        data.forEach(o->{
            // 1 是跳过节假日
            if ("1".equals(o.getSkipHolidayFlag()) && finalSpecialFlag ==1){
                return;
            }
//            LightMsg request =LightMsg.builder().device(o.getAddress()).value(o.getCode()).floor(o.getFloor()).type("2");
//            iLightService.controlLight();
        });
    }


}
