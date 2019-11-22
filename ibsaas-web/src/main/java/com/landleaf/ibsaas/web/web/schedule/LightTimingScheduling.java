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
import com.landleaf.ibsaas.web.web.service.light.ILightSceneTimingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DateTime.now;

/**
 * @description: 灯光场景定时
 * 每月一号00:15分跑
 */
@Component
@EnableScheduling
@Slf4j
public class LightTimingScheduling {

    @Autowired
    private RedisHandle redisHandle;
    @Autowired
    private ILightSceneTimingService iLightSceneTimingService;


    @Scheduled(cron = "0 * * * * *")
    public void excutelightTiming(){

        LocalDateTime nowDate = LocalDateTime.now();
        iLightSceneTimingService.executeTime(nowDate);

    }

}
