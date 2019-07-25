package com.landleaf.ibsaas.web.web.service.light.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.internal.LinkedTreeMap;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.knight.control.QueryMjDoorByIdDTO;
import com.landleaf.ibsaas.common.domain.knight.control.Station;
import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.enums.knight.KnightSubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class LightService implements ILightService {

    @Autowired
    private WebMqProducer webMqProducer;


    @Override
    public void controlLight(LightMsg requestBody) {
        webMqProducer.sendMessage(JSONUtil.toJsonStr(requestBody),
                TopicConstants.TOPIC_LIGHT_CONTROL,
                TagConstants.TAGS_DEFAULT);
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
