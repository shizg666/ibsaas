package com.landleaf.ibsaas.web.web.service.light.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LightService implements ILightService {

    @Autowired
    private WebMqProducer webMqProducer;
    @Autowired
    private RedisHandle redisHandle;


    @Override
    public void controlLight(LightMsg requestBody) {
        webMqProducer.sendMessage(JSONUtil.toJsonStr(requestBody),
                TopicConstants.TOPIC_LIGHT_CONTROL,
                TagConstants.TAGS_DEFAULT);
    }


    @Override
    public String getTryLightState(String key, String adress, Long timeout) {
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        try {
            while (System.currentTimeMillis() < expireTimeMillis) {
                Thread.sleep(200L);
                String state = redisHandle.getMapField(key,adress);
                if (StringUtil.isNotEmpty(state)) {
                    return  state;
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            log.error("获取设备状态失败：{}",e.getMessage());
            return "0";
        }
        return "0";
    }


    /**
     * 获取灯光业务响应数据
     *
     * @param requestBody 请求参数
     * @param msgName     请求消息类型
     * @param subMsgName  请求消息子类型
     * @return
     */
//    private Response sendMsgWaitForResult(Object requestBody, String msgName, String subMsgName) {
//
//
//        Response response = new Response();
//        KnightMessage request = new KnightMessage();
//        String msgId = MessageUtil.generateId(20);
//        request.setMsgId(msgId);
//        request.setMsgName(msgName);
//        request.setSubMsgName(subMsgName);
//        request.setRequestBody(requestBody);
//        webMqProducer.sendMessage(JSON.toJSONString(request), CLIENT_TOPIC, TagConstants.TAGS_DEFAULT);
//        Future<KnightMessage> cacheFuture = futureService.getKnightCacheFuture(msgId, TIME_OUT);
//        KnightMessage result = new KnightMessage();
//        try {
//            result = cacheFuture.get(TIME_OUT, TimeUnit.MILLISECONDS);
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        if (result != null && result.getResponse() != null) {
//            Object tmpResult = result.getResponse().getResult();
//            if (tmpResult != null) {
//
//                LinkedTreeMap knightResponse = (LinkedTreeMap) result.getResponse().getResult();
//                response.setMessage((String) knightResponse.get("resultInfo"));
//                response.setResult(knightResponse.get("obj"));
//                String resultCode = (String) knightResponse.get("result");
//                if (StringUtil.isEquals("200", resultCode)) {
//                    response.setSuccess(true);
//                } else {
//                    throw new BusinessException((String) knightResponse.get("resultInfo"));
//                }
//            }
//            return response;
//        }
//        throw new BusinessException("未获取到数据,稍后再试");
//    }


}
