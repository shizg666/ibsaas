package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.ISensorWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/10 13:55
 * @description:
 */
@Service
@Slf4j
public class SensorWebService extends BaseDeviceService implements ISensorWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<SensorVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(HvacConstant.SENSOR_PORT));
    }

    @Override
    public SensorVO getInfoById(String id) {
        List<SensorVO> sensorVOList = overview();
        for (SensorVO ss:sensorVOList){
            if(ss.getId().equals(id)){
                return ss;
            }
        }
        return null;
    }
}
