package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IFanCoilWebService;
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
 * @date 2019/6/10 14:31
 * @description:
 */
@Service
@Slf4j
public class FanCoilWebService extends BaseDeviceService implements IFanCoilWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<FanCoilVO> overview() {
        List<FanCoilVO> fanCoilVOList = redisHandle.getMapField(placeId, String.valueOf(HvacConstant.FAN_COIL_PORT_1));
        List<FanCoilVO> fanCoilVOList2 = redisHandle.getMapField(placeId, String.valueOf(HvacConstant.FAN_COIL_PORT_2));
        fanCoilVOList.addAll(fanCoilVOList2);
        return fanCoilVOList;
    }

    public Map<String, Map<String, FanCoilVO>> totalOverView(){
        //查找所有的风机节点
        List<HvacNode> hvacNodes = hvacNodeDao.getHvacNodes(new ArrayList<Integer>(){{
            add(HvacConstant.FAN_COIL_PORT_1);
            add(HvacConstant.FAN_COIL_PORT_2);
        }});

        Map<String, Map<String, FanCoilVO>> result = new HashMap<>(8);
        List<FanCoilVO> overview = overview();
        overview.forEach(fc -> {



        });
        return result;
    }


    @Override
    public FanCoilVO getInfoById(String id) {
        List<FanCoilVO> fanCoilVOList = overview();
        for (FanCoilVO fc:fanCoilVOList){
            if(fc.getId().equals(id)){
                return fc;
            }
        }
        return null;
    }

    @Override
    public void update(FanCoilDTO fanCoilDTO) {
        checkWritePermission(fanCoilDTO);
        HvacMqMsg msg = new HvacMqMsg(NewFanDTO.class.getName(), JSONUtil.toJsonStr(fanCoilDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);

    }
}
