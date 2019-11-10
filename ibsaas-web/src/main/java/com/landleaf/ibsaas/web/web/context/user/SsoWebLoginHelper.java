package com.landleaf.ibsaas.web.web.context.user;

import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class SsoWebLoginHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(SsoWebLoginHelper.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 获取用户
     *
     * @param sid
     * @return
     */
    public User get(String sid) {
        User sysUser = (User) redisUtil.get(sid);
        return sysUser;
    }

    /**
     * 设置用户
     *
     * @param sid
     * @param user
     * @param timeout 失效时间
     * @return
     */
    public boolean set(String sid, User user, long timeout) {
        return redisUtil.set(sid, user, timeout);
    }

    /**
     * 清除用户
     */
    public boolean remove(String sid) {
        Boolean delete = false;
        try {
             delete = redisTemplate.delete(sid);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return delete;
    }
}
