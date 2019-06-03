package com.landleaf.ibsaas.client.hvac.rokectmq;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.rocketmq.annotation.MQConsumeService;
import com.landleaf.ibsaas.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.ibsaas.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/3 16:14
 * @description: 通用hvac处理mq请求
 */
@MQConsumeService(topic = TopicConstants.TOPIC_HVAC_WRITE, tags = {TagConstants.TAGS_DEFAULT})
@Component
@Slf4j
public class ConsumerMessageForHvac extends AbstractMQMsgProcessor {

    @Autowired
    private ICommonDeviceService iCommonDeviceService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt messageExt) {

        HvacMqMsg hvacMqMsg =
                MessageUtil.getInstance().getGson().fromJson(new String(messageExt.getBody()), HvacMqMsg.class);
        log.info("收到消息[topic:{}];[tag:{}];[消息:{}]", TopicConstants.TOPIC_HVAC_WRITE, tag, hvacMqMsg);

        MQConsumeResult result = new MQConsumeResult();
        try {
            Class<?> aClass = Class.forName(hvacMqMsg.getClazzPath());
            String reqBody = hvacMqMsg.getReqBody();
            Object fromJson = MessageUtil.getInstance().getGson().fromJson(reqBody, aClass);
            BaseDevice o = (BaseDevice) fromJson;
            iCommonDeviceService.writeDevice(o);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            log.error("------------------------------>处理mq中写请求失败:{}",e.getMessage(),e);
        }

        return result;
    }
}
