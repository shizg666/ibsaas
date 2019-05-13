package com.landleaf.ibsaas.web.web.controller;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.knight.KngihtMessage;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        KngihtMessage result = new KngihtMessage();
        KngihtMessage kngihtMessage = new KngihtMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        webMqProducer.sendMessage(JSON.toJSONString(kngihtMessage),"ibsaas_knight_lifang_lgc_1921681010", TagConstants.TAGS_DEFAULT);
        Future<KngihtMessage> cacheFuture = futureService.getKnightCacheFuture("123", timeout);
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
