//package com.landleaf.ibsaas.web.web.schedul;
//
//import com.alibaba.fastjson.JSON;
//import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
//import com.landleaf.ibsaas.common.redis.RedisUtil;
//import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class UpdateRedisStatus implements InitializingBean{
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRedisStatus.class);
//    @Autowired
//    private RedisUtil redisUtil;
//    @Autowired
//    private IConfigSettingService configSettingService;
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Scheduled(cron = "0 0/10 * * * ? ")   //每10秒执行一次
//    public void updateConfigSettingStatus() {
//        LOGGER.info("定时任务更新配置缓存执行......");
//        List<ConfigSettingVO> allConfigSetting = configSettingService.selectList();
//        Map<String, List<ConfigSettingVO>> cofigGroup = allConfigSetting.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingType));
//        try {
//            //查找key
//            redisTemplate.delete(RedisUtil.T_CONFIG_SETTING);
//            cofigGroup.forEach((i, v) -> {
//                redisTemplate.opsForHash().put(RedisUtil.T_CONFIG_SETTING, i, JSON.toJSONString(v));
//            });
//            LOGGER.info("定时任务更新配置缓存执行结束......");
//        } finally {
//        }
//
//
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        updateConfigSettingStatus();
//    }
//}
