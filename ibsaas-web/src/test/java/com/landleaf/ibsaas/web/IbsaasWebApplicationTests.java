package com.landleaf.ibsaas.web;


import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;


/**
 * @author Lokiy
 * @date 2019/5/28 12:20
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasWebApplicationTests {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
    }


    @Test
    public void redisOp(){
        redisHandle.addMap("lgc", "abc", "aaa");
        redisHandle.addMap("lgc", "123", "bbb");
        redisHandle.addMap("lgc", String.valueOf(new Integer(3002)), new ArrayList<String>(){{add("1");add("2");add("3");}});
        redisHandle.addMap("lgc", String.valueOf(new Integer(789)), "ddd");




    }
}
