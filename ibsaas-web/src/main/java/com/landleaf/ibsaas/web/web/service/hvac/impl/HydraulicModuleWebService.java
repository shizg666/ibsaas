package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.HydraulicModule;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<HydraulicModuleVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(HvacConstant.HYDRAULIC_MODULE_PORT));
    }

    @Override
    public Map<String, Map<String, HydraulicModuleVO>> totalOverview() {
        //查找所有水力模块节点
        List<HvacNode> hvacNodes = hvacNodeDao.getHvacNodes(new ArrayList<Integer>(){{
            add(HvacConstant.HYDRAULIC_MODULE_PORT);
        }});
        Map<String, HvacNode> map = hvacNodes.stream().collect(Collectors.toMap(HvacNode::getId, hn -> hn));
        //水力模块归类
        Map<String, Map<String, HydraulicModuleVO>> result = new HashMap<>(8);
        List<HydraulicModuleVO> overview = overview();
        overview.forEach(hm -> {
            HvacNode temp = map.get(hm.getId());
            result.computeIfAbsent(String.valueOf(temp.getFloor()), k -> new HashMap<>(4));
            result.get(String.valueOf(temp.getFloor())).put(temp.getNodeName(), hm);
        });
        return result;
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
