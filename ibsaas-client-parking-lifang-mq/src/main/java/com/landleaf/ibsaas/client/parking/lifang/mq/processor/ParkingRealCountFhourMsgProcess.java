package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChannelService;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IUsercrdtmService;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分时段查询实时车位数
 */
@Component
public class ParkingRealCountFhourMsgProcess extends  BaseProcess  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingRealCountFhourMsgProcess.class);
    @Autowired
    private IUsercrdtmService usercrdtmService;

    /**
     * 根据类型查询通道列表
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse realCountFHour(UsercrdtmRealCountQueryByHourDTO requestBody) {
        LOGGER.info("收到【分时段查询实时车位数】请求,{}", JSON.toJSONString(requestBody));
        List result = usercrdtmService.realCountFHour(requestBody);
        return returnSuccess(result);
    }


}
