package com.landleaf.ibsaas.web.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.rocketmq.annotation.MQConsumeService;
import com.landleaf.ibsaas.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.ibsaas.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 门禁客户端返回数据消费处理
 */
@MQConsumeService(topic = TopicConstants.TOPIC_KNIGHT, tags = {TagConstants.TAGS_DEFAULT})
@Component
public class ComsumerMessageForKnight extends AbstractMQMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComsumerMessageForKnight.class);

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        LOGGER.info("消费消息msgid:{}", message.getMsgId());
        try {
            String topic = TopicConstants.TOPIC_KNIGHT;
            String msgBody = new String(message.getBody(), "utf-8");
            LOGGER.info("收到消息[topic:{}];[tag:{}];[消息:{}]", topic, tag, msgBody);
            //解析消息
            KnightMessage kngihtMessage = MessageUtil.getInstance().getGson().fromJson(msgBody, new TypeToken<KnightMessage>() {
            }.getType());
            // TODO 后期修改放入redis缓存
            try {
                //业务数据放入缓存
                ConcurrentHashMapCacheUtils.setCache(kngihtMessage.getMsgId(), kngihtMessage, 60 * 1000L);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }


        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }

}
