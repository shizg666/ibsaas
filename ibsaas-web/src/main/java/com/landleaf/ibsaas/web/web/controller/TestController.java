package com.landleaf.ibsaas.web.web.controller;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private WebMqProducer webMqProducer;
    @Autowired
    private IFutureService futureService;

    @GetMapping("mq")
    public String testMq(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("example");
        List<User> tmp = Lists.newArrayList();
        User user = new User();
        user.setEmail("1244");
        tmp.add(user);
        kngihtMessage.setRequestBody(tmp);
        webMqProducer.sendMessage(JSON.toJSONString(kngihtMessage),"ibsaas_knight_lifang_lgc_1921681010", TagConstants.TAGS_DEFAULT);
        Future<KnightMessage> cacheFuture = futureService.getKnightCacheFuture("123", timeout);
        try {
            result = cacheFuture.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }
}
