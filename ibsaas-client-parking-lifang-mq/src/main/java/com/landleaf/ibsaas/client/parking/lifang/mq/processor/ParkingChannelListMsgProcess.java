package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChannelService;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通道消息处理类
 */
@Component
public class ParkingChannelListMsgProcess extends  BaseProcess  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingChannelListMsgProcess.class);
    @Autowired
    private IChannelService channelService;

    /**
     * 根据类型查询通道列表
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse queryChannelByType(ChannelListQueryDTO requestBody) {
        LOGGER.info("收到【根据类型查询通道列表】请求,{}", JSON.toJSONString(requestBody));
        List result = channelService.queryChannelByType(requestBody);
        return returnSuccess(result);
    }


}
