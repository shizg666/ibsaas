package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HydraulicModuleVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IHydraulicModuleWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/10 13:34
 * @description:
 */
@Service
@Slf4j
public class HydraulicModuleWebService extends BaseDeviceService implements IHydraulicModuleWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<HydraulicModuleVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(HvacConstant.HYDRAULIC_MODULE_PORT));
    }

    @Override
    public HydraulicModuleVO getInfoById(String id) {
        List<HydraulicModuleVO> hydraulicModuleVOList = overview();
        for (HydraulicModuleVO hm:hydraulicModuleVOList){
            if(hm.getId().equals(id)){
                return hm;
            }
        }
        return null;
    }

    @Override
    public void update(HydraulicModuleVO hydraulicModuleVO) {
        checkWritePermission(hydraulicModuleVO);
        HvacMqMsg msg = new HvacMqMsg(NewFanDTO.class.getName(), JSONUtil.toJsonStr(hydraulicModuleVO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }


}
