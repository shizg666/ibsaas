package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.dto.ExhaustBlowerDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.ExhaustBlowerVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IExhaustBlowerService;
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
public class ExhaustBlowerService extends BaseDeviceService implements IExhaustBlowerService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;


    @Override
    public List<ExhaustBlowerVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.EXHAUST_BLOWER.getDeviceType()));
    }

    @Override
    public void update(ExhaustBlowerDTO exhaustBlowerDTO) {
        checkWritePermission(exhaustBlowerDTO);
        HvacMqMsg msg = new HvacMqMsg(ExhaustBlowerDTO.class.getName(), JSONUtil.toJsonStr(exhaustBlowerDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }
}
