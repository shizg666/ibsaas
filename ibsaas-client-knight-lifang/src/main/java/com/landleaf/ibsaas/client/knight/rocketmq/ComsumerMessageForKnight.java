package com.landleaf.ibsaas.client.knight.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.client.knight.asyn.IFutureService;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.annotation.MQConsumeService;
import com.landleaf.ibsaas.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.ibsaas.rocketmq.consumer.processor.MQConsumeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 门禁客户端返回数据消费处理
 */
@MQConsumeService(topic = "${rocketmq.consumer.topic}", tags = TagConstants.TAGS_DEFAULT)
@Component
public class ComsumerMessageForKnight extends AbstractMQMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComsumerMessageForKnight.class);

    @Autowired
    private IFutureService futureService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        LOGGER.info("消费消息msgid:{}", message.getMsgId());
        try {
            String topic = "ibsaas_knight_lifang_lgc_1921681010";
            String msgBody = new String(message.getBody(), "utf-8");
            LOGGER.info("收到消息[topic:{}];[tag:{}];[消息:{}]", topic, tag, msgBody);
            //解析消息
            KnightMessage kngihtMessage = MessageUtil.getInstance().getGson().fromJson(msgBody, new TypeToken<KnightMessage>() {
            }.getType());

            futureService.handlerMsg(kngihtMessage);
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
