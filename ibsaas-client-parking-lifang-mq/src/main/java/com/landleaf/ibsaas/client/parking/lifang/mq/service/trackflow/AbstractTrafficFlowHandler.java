package com.landleaf.ibsaas.client.parking.lifang.mq.service.trackflow;

import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 所有传感器指数处理类继承该类
 */
public abstract class AbstractTrafficFlowHandler implements TrafficFlowHandler {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTrafficFlowHandler.class);

    @Override
    public List<UsercrdtmInHistoryQueryDTO> handle(String code, UsercrdtmInHistoryQueryDTO queryDTO) {
        return this.handle(code,queryDTO);
    }
}
