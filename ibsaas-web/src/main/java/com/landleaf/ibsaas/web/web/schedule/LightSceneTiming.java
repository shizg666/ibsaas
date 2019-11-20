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
 * @description: 灯光场景定时
 * 每月一号00:15分跑
 */
@Component
@EnableScheduling
@Slf4j
public class LightSceneTiming {

    @Autowired
    private RedisHandle redisHandle;
    @Scheduled(cron = "0 * * * * *")

    @Async("lightSceneTimeThreadPool")
    public void excuteLightSceneTiming(){



    }

}
