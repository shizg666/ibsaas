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
        //这里进去是string
        redisHandle.addMap("lcc", "456", "bbb");




    }
}
