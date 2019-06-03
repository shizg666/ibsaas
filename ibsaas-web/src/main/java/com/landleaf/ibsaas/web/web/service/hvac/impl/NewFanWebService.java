package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.INewFanWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/3 9:55
 * @description:
 */
@Service
public class NewFanWebService implements INewFanWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<NewFanVO> overview() {
        Map<String, Object> map = redisHandle.getMap(placeId);
        Object mapField = map.get(3002);
        return (List<NewFanVO>) mapField;
    }

    @Override
    public NewFanVO getInfoById(String id) {
        List<NewFanVO> newFanVOList = overview();
        for (NewFanVO nf:newFanVOList){
            if(nf.getId().equals(id)){
                return nf;
            }
        }
        return null;
    }

    @Override
    public void update(NewFanDTO newFanDTO) {
        HvacMqMsg msg = new HvacMqMsg(NewFanDTO.class.getName(), JSONUtil.toJsonStr(newFanDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }
}
