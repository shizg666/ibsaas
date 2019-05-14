package com.landleaf.ibsaas.client.knight.processor.knight;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.leo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息处理类
 */
@Component
public class KnightExampleMsgProcess {


    private static final Logger LOGGER = LoggerFactory.getLogger(KnightExampleMsgProcess.class);

    public User handle(List<User> requestBody) {
        LOGGER.info("收到example处理请求,{}", JSON.toJSONString(requestBody));
        User result = new User();
        result.setMobile("19945657236");
        //返回数据
        return result;
    }

}
