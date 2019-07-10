package com.landleaf.ibsaas.client.light.rokectmq;


import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.ibsaas.client.light.service.LightService;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
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
 * @author shizg
 * @description: 通用处理灯光的消息
 */
@MQConsumeService(topic = TopicConstants.TOPIC_LIGHT_CONTROL, tags = {TagConstants.TAGS_DEFAULT})
@Component
@Slf4j
public class ConsumerMessageForLight extends AbstractMQMsgProcessor {

    @Autowired
    private LightService lightService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt messageExt) {
        LightMsg lightMsg = null;
        try{
            lightMsg = MessageUtil.getInstance().getGson().fromJson(new String(messageExt.getBody()), LightMsg.class);
            log.info("收到消息[topic:{}];[tag:{}];[消息:{}]", TopicConstants.TOPIC_LIGHT_CONTROL, tag, lightMsg);
            lightService.lightHandle(lightMsg);
        }catch (Exception e){
            log.error("灯控消息消费失败！{},[topic:{}];[tag:{}];[消息:{}]",e.getMessage(),TopicConstants.TOPIC_LIGHT_CONTROL, tag, lightMsg);
        }
        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }
}
