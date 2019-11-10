package com.landleaf.ibsaas.client.knight.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.landleaf.ibsaas.client.knight.utils.RandomUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * rocketmq生产者
 */
@Component
public class KnightMqProducer {

    public static final Logger LOGGER = LoggerFactory.getLogger(KnightMqProducer.class);

    @Autowired
    private DefaultMQProducer rocketmqProducer;

    @Value("${rocketmq.producer.topic}")
    private String topic;
    @Value("${rocketmq.producer.tags}")
    private String tag;

    //发送数据
    public void sendMessageForWeb(String sendMsg) {
        String key = RandomUtil.generateNumberString(10);
        Message msg = null;
        try {
            msg = new Message(topic, tag, key, sendMsg.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("发送消息编码格式转换错误{}",e.getMessage(),e);
        }
        try {
            Message finalMsg = msg;
            rocketmqProducer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("发送MQ消息成功【topic】:{};【tag】:{};【消息】:{}", topic, tag, finalMsg.toString());
                    sendResult.getSendStatus();
                }

                @Override
                public void onException(Throwable e) {
                    LOGGER.error("发送MQ消息失败:【topic】:{};【tag】:{};消息{};异常:{}", topic, tag, finalMsg.toString(), e.getMessage(), e);
                }
            });
        } catch (MQClientException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (RemotingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
