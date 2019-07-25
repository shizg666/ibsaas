package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.dto.RainwaterCollectionDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IRainwaterCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:58
 * @description:
 */
@Service
@Slf4j
public class RainwaterCollectionService extends BaseDeviceService implements IRainwaterCollectionService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<RainwaterCollectionVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.RAINWATER_COLLECTION.getDeviceType()));
    }

    @Override
    public void update(RainwaterCollectionDTO rainwaterCollectionDTO) {
        checkWritePermission(rainwaterCollectionDTO);
        HvacMqMsg msg = new HvacMqMsg(RainwaterCollectionDTO.class.getName(), JSONUtil.toJsonStr(rainwaterCollectionDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }
}
