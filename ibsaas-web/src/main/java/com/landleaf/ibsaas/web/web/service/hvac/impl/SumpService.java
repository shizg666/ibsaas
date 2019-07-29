package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.domain.hvac.vo.SumpVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.ISumpService;
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
public class SumpService extends BaseDeviceService implements ISumpService {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;


    @Override
    public List<SumpVO> overview() {
//        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.SUMP.getDeviceType()));
        return (List<SumpVO>) baseOverview(BacnetDeviceTypeEnum.SUMP.getDeviceType());
    }
}
