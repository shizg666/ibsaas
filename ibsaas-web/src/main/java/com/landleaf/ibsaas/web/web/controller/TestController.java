package com.landleaf.ibsaas.web.web.controller;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.knight.*;
import com.landleaf.ibsaas.common.domain.knight.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceResultDTO;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.emply.*;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.redis.energy.ConfigSettingRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
        kngihtMessage.setSubMsgName("queryDepart");
        QueryDepartDTO requestBody = new QueryDepartDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(100);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/addDepart")
    public String testAddDepart(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("addDepart");
        AddDepartDTO requestBody = new AddDepartDTO();
        requestBody.setParentId(1);
        requestBody.setName("测试部门");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/deleteDepart")
    public String testDeleteDepart(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("deleteDepart");
       DeleteDepartDTO requestBody = new DeleteDepartDTO();
        requestBody.setDepartId(15);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getAllEmplyList")
    public String getAllEmplyList(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getAllEmplyList");
        QueryEmplyDTO requestBody = new QueryEmplyDTO();
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/selectEmply")
    public String selectEmply(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("selectEmply");
        QueryEmplyDTO requestBody = new QueryEmplyDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(100);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/addEmply")
    public String addEmply(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("addEmply");
        AddEmplyDTO requestBody = new AddEmplyDTO();
        requestBody.setEmployeeName("测试用户");
        requestBody.setDepartId(15);
        requestBody.setEmpAddress("LGC");
        requestBody.setEmployeeId("0001");
        requestBody.setPhone("18677778888");
        requestBody.setEmployeeSex("1");
        requestBody.setEmployeeType("A");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/deleteEmply")
    public String deleteEmply(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("deleteEmply");
        DeleteEmplyDTO requestBody = new DeleteEmplyDTO();
        requestBody.setSysNo("355");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/sendCard")
    public String sendCard(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("sendCard");
        SendCardDTO requestBody = new SendCardDTO();
        requestBody.setSysNo(355);
        requestBody.setSerial("00000000ABCDEFGH");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/deleteCard")
    public String deleteCard(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("deleteCard");
        DeleteCardDTO requestBody = new DeleteCardDTO();
        requestBody.setSysNo(355);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/updateEmply")
    public String updateEmply(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("updateEmply");
        UpdateEmplyDTO requestBody = new UpdateEmplyDTO();
        requestBody.setEmployeeName("测试用户222");
        requestBody.setDepartId(15);
        requestBody.setEmpAddress("LGC111");
        requestBody.setEmployeeId("0002");
        requestBody.setPhone("18677777777");
        requestBody.setEmployeeSex("1");
        requestBody.setEmployeeType("A");
        requestBody.setSysNo(355);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/registeruser")
    public String registeruser(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("registeruser");
        RegisterUserDTO requestBody = new RegisterUserDTO();
        requestBody.setEmployeeId("357");
        requestBody.setDoorId("55");
        requestBody.setStartTime("20100301165600");
        requestBody.setEndTime("20100301165600");
        requestBody.setIsSpecial(0);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/registeruserByDb")
    public String registeruserByDb(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("registeruserByDb");
        RegisterUserByDbDTO requestBody = new RegisterUserByDbDTO();
        requestBody.setSysNo(356);
        requestBody.setDoorId(14);
        requestBody.setStartTime(new Date());
        requestBody.setEndTime(DateUtils.convert("2066-09-29 00:00:00"));
        requestBody.setSpecial(0);
        requestBody.setDeviceId(12);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/queryRegisteruserByDb")
    public String queryRegisteruserByDb(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("queryRegisteruserByDb");
        QueryRegisterUserByDbDTO requestBody = new QueryRegisterUserByDbDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(10);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/unregisteruser")
    public String unregisteruser(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("unregisteruser");
        UnRegisterUserDTO requestBody = new UnRegisterUserDTO();
        requestBody.setEmployeeId(314);
        requestBody.setDoorId(14);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/unregisteruserByDb")
    public String unregisteruserByDb(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("unregisteruserByDb");
        UnRegisterUserByDbDTO requestBody = new UnRegisterUserByDbDTO();
        requestBody.setId(120171);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getMjDeviceAll")
    public String getMjDeviceAll(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getMjDeviceAll");
        QueryMjDeviceDTO requestBody = new QueryMjDeviceDTO();
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getMjDeviceById")
    public String getMjDeviceById(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getMjDeviceById");
        QueryMjDeviceDTO requestBody = new QueryMjDeviceDTO();
        requestBody.setDeviceSysId(9);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getDoorInfoAll")
    public String getDoorInfoAll(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getDoorInfoAll");
        QueryMjDoorDTO requestBody = new QueryMjDoorDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(100);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getDoorInfoById")
    public String getDoorInfoById(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getDoorInfoById");
        QueryMjDoorByIdDTO requestBody = new QueryMjDoorByIdDTO();
        requestBody.setDoorId(65);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/mjOpenDoorRecord")
    public String mjOpenDoorRecord(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("mjOpenDoorRecord");
        QueryMjDoorOpenRecordDTO requestBody = new QueryMjDoorOpenRecordDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(100);
        requestBody.setStart("2019-05-10 11:11:11");
        requestBody.setEnd("2019-05-10 12:11:11");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/mJUrgentEventRecord")
    public String mJUrgentEventRecord(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("mJUrgentEventRecord");
        QueryMjUrgentEventRecordDTO requestBody = new QueryMjUrgentEventRecordDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(100);
        requestBody.setStart("2019-01-10 11:11:11");
        requestBody.setEnd("2019-05-10 12:11:11");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getAttendanceResult")
    public String getAttendanceResult(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getAttendanceResult");
        QueryAttendanceResultDTO requestBody = new QueryAttendanceResultDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(2);
        requestBody.setBgnDate("2019-05-9 11:11:11");
        requestBody.setEndDate("2019-05-10 12:11:11");
        requestBody.setIgnoreDptId(1);
        requestBody.setIgnoreSysNo(1);
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/getAttendanceRecord")
    public String getAttendanceRecord(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("getAttendanceRecord");
        QueryAttendanceRecordDTO requestBody = new QueryAttendanceRecordDTO();
        requestBody.setCurPage(1);
        requestBody.setPageSize(2);
        requestBody.setBgnDate("2019-05-9 11:11:11");
        requestBody.setEndDate("2019-05-10 12:11:11");
        kngihtMessage.setRequestBody(requestBody);
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
    @GetMapping("mq/setAttendanceRecord")
    public String setAttendanceRecord(){
        KnightMessage result = new KnightMessage();
        KnightMessage kngihtMessage = new KnightMessage();
        Long timeout =30 * 1000L;
        kngihtMessage.setMsgId("123");
        kngihtMessage.setMsgName("knight");
        kngihtMessage.setSubMsgName("setAttendanceRecord");
        AddAttendanceRecordDTO requestBody = new AddAttendanceRecordDTO();
        requestBody.setCardTime("2017-06-30 00:00:00");
        requestBody.setDescribe("测试,请忽略");
        requestBody.setDoorId(55);
        requestBody.setSerial("000000005D444004");
        kngihtMessage.setRequestBody(requestBody);
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

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("redisTemplate/del")
    public String testRedisTemplate(){

        Boolean test = redisTemplate.delete("test");
        return "OK";
    }
    @GetMapping("redisTemplate/save")
    public String testRedisTemplateSave(){
        User user = new User();
        user.setUserCode("123456");
        redisTemplate.opsForValue().set("test", user, 60, TimeUnit.SECONDS);
        return "OK";
    }
    @GetMapping("redisTemplate/get")
    public String testRedisTemplateGet(){
        User user= (User) redisTemplate.opsForValue().get("test");
        return "OK";
    }

    @Autowired
    private ConfigSettingRedis configSettingRedis;
    @GetMapping("redisTemplate/getConfig")
    public String testRedisTemplateGetConfig(){

        List<ConfigSettingVO> areaList = configSettingRedis.getConfigSettingVOByType("equip_area");
        return "OK";
    }
}
