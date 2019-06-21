package com.landleaf.ibsaas.web.web.redis.energy;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.List;


/**
 * 缓存配置类
 */
@Component
public class ConfigSettingRedis {

    @Autowired
    private IConfigSettingService configSettingService;

    @Autowired
    private RedisUtil redisUtil;

    public List<ConfigSettingVO> getConfigSettingVOByType(String type){

        Jedis jedis = redisUtil.getJedis();
        List<ConfigSettingVO> result = null;
        try {
            List<String> list = jedis.hmget(RedisUtil.T_CONFIG_SETTING, type);
            if (list == null || list.size() == 0) {
                return null;
            }
            result= JSON.parseArray(list.get(0), ConfigSettingVO.class);

            if (result == null) {
                List<ConfigSettingVO> queryResult = configSettingService.typeList(type);
                if(!CollectionUtils.isEmpty(queryResult)){
                    jedis.hset(RedisUtil.T_CONFIG_SETTING, type, JSON.toJSONString(queryResult));
                }
                return queryResult;
            }
            return result;
        } finally {
            jedis.close();
        }

    }

}



