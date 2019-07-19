package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.dto.DomesticWaterDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.DomesticWaterVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IDomesticWaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:59
 * @description:
 */
@Service
@Slf4j
public class DomesticWaterService extends BaseDeviceService implements IDomesticWaterService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<DomesticWaterVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.DOMESTIC_WATER.getDeviceType()));
    }

    @Override
    public void update(DomesticWaterDTO domesticWaterDTO) {
        checkWritePermission(domesticWaterDTO);
        HvacMqMsg msg = new HvacMqMsg(DomesticWaterDTO.class.getName(), JSONUtil.toJsonStr(domesticWaterDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }
}
