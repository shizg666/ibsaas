package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IUsercrdtmService;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmListQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 进出记录列表
 */
@Component
public class ParkingUsercrdtmMsgProcess extends BaseProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingUsercrdtmMsgProcess.class);
    @Autowired
    private IUsercrdtmService usercrdtmService;

    /**
     * 进出记录列表
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse userCrdtmList(UsercrdtmListQueryDTO requestBody) {
        LOGGER.info("收到【进出记录列表】请求,{}", JSON.toJSONString(requestBody));
        PageInfo pageInfo = usercrdtmService.pageQueryList(requestBody);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("total", pageInfo.getTotal());
        resultMap.put("list", pageInfo.getList());
        return returnSuccess(resultMap);
    }

    /**
     * 车流量历史查询
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse trafficFlow(UsercrdtmInHistoryQueryDTO requestBody) {
        LOGGER.info("收到【车流量历史查询】请求,{}", JSON.toJSONString(requestBody));
        List list = usercrdtmService.trafficFlow(requestBody);
        return returnSuccess(list);
    }


}
