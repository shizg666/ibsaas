package com.landleaf.ibsaas.web.web.schedul;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateRedisStatus implements InitializingBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRedisStatus.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IConfigSettingService configSettingService;

    @Scheduled(cron = "0 0/15 * * * ? ")   //每10秒执行一次
    public void updateConfigSettingStatus() {
        LOGGER.info("定时任务更新配置缓存执行......");
        Jedis jedis = null;
        List<ConfigSettingVO> allConfigSetting = configSettingService.selectList();
        Map<String, List<ConfigSettingVO>> cofigGroup = allConfigSetting.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingType));
        try {
            jedis = redisUtil.getJedis();
            //查找key
            jedis.del(RedisUtil.T_CONFIG_SETTING);
            Jedis finalJedis = jedis;
            cofigGroup.forEach((i, v) -> {
                finalJedis.hset(RedisUtil.T_CONFIG_SETTING, i, JSON.toJSONString(v));
            });
            LOGGER.info("定时任务更新配置缓存执行结束......");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateConfigSettingStatus();
    }
}
