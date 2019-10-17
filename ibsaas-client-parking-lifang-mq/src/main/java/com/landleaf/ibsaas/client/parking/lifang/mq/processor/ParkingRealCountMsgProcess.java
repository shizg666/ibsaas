package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IUsercrdtmService;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 车位实时监控
 */
@Component
public class ParkingRealCountMsgProcess extends  BaseProcess  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingRealCountMsgProcess.class);
    @Autowired
    private IUsercrdtmService usercrdtmService;

    /**
     * 根据类型查询通道列表
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse realCount(UsercrdtmRealCountQueryDTO requestBody) {
        LOGGER.info("收到【车位实时监控】请求,{}", JSON.toJSONString(requestBody));
        UsercrdtmRealCountQueryDTO result = usercrdtmService.realCount(requestBody);
        return returnSuccess(result);
    }


}
