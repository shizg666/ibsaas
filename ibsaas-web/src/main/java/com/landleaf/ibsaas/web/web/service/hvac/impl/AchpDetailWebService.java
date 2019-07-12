package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpDetailDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpMonitorDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpPumpValveDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpDetailVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpMonitorVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpPumpValveVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IAchpDetailWebService;
import com.sun.tools.javac.comp.Lower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/7/4 10:05
 * @description: ahu逻辑处理类
 */
@Service
@Slf4j
public class AchpDetailWebService extends BaseDeviceService implements IAchpDetailWebService {

    private static final String HIGH = "high";

    private static final String LOW = "low";

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;


    @Override
    public List<AchpDetailVO> overviewDetail() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.ACHP_DETAIL.getDeviceType()));
    }


    @Override
    public Map<String, List<AchpDetailVO>> totalOverviewDetail(){
        Map<String, List<AchpDetailVO>> result = new HashMap<String, List<AchpDetailVO>>(2){{
            put(HIGH, new ArrayList<>());
            put(LOW, new ArrayList<>());
        }};
        List<AchpDetailVO> overview = overviewDetail();
        for (int i = 0; i < overview.size(); i++) {
            if(i < 3){
                result.get(HIGH).add(overview.get(i));
            }else {
                result.get(LOW).add(overview.get(i));
            }
        }
        return result;
    }

    @Override
    public void updateDetail(AchpDetailDTO achpDetailDTO) {
        checkWritePermission(achpDetailDTO);
        HvacMqMsg msg = new HvacMqMsg(AchpDetailDTO.class.getName(), JSONUtil.toJsonStr(achpDetailDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);
    }

    @Override
    public List<AchpPumpValveVO> overviewPumpValve() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.ACHP_PUMP_VALVE.getDeviceType()));
    }

    @Override
    public void updatePumpValve(AchpPumpValveDTO achpPumpValveDTO) {
        checkWritePermission(achpPumpValveDTO);
        HvacMqMsg msg = new HvacMqMsg(AchpPumpValveDTO.class.getName(), JSONUtil.toJsonStr(achpPumpValveDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);
    }

    @Override
    public List<AchpMonitorVO> overviewMonitor() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.ACHP_MONITOR.getDeviceType()));
    }

    @Override
    public void updateMonitor(AchpMonitorDTO achpMonitorDTO) {
        checkWritePermission(achpMonitorDTO);
        HvacMqMsg msg = new HvacMqMsg(AchpMonitorDTO.class.getName(), JSONUtil.toJsonStr(achpMonitorDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);
    }


}
