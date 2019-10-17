package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChargeruleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 收费类型列表消息处理类
 */
@Component
public class ParkingChargeRuleListMsgProcess extends  BaseProcess  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingChargeRuleListMsgProcess.class);
    @Autowired
    private IChargeruleService chargeruleService;

    /**
     * 根据类型查询通道列表
     *
     * @return
     */
    public ParkingResponse queryAllChargerule(Object o) {
        LOGGER.info("收到【收费类型列表】请求");
        List result = chargeruleService.queryAllChargerule();
        return returnSuccess(result);
    }


}
