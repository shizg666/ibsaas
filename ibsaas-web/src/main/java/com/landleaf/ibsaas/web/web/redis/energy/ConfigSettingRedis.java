package com.landleaf.ibsaas.web.web.redis.energy;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.List;


/**
 * 缓存配置类
 */
@Component
public class ConfigSettingRedis {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigSettingRedis.class);

    @Autowired
    private IConfigSettingService configSettingService;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<ConfigSettingVO> getConfigSettingVOByType(String type){

        List<ConfigSettingVO> result = null;
        try {
            String tempResult = (String) redisTemplate.opsForHash().get(RedisUtil.T_CONFIG_SETTING, type);
            if (StringUtil.isEmpty(tempResult)) {
                return null;
            }
            result= JSON.parseArray(tempResult, ConfigSettingVO.class);

            if (result == null) {
                List<ConfigSettingVO> queryResult = configSettingService.typeList(type);
                if(!CollectionUtils.isEmpty(queryResult)){
                    redisTemplate.opsForHash().put(RedisUtil.T_CONFIG_SETTING, type, JSON.toJSONString(queryResult));
                }
                return queryResult;
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return result;
    }

}



