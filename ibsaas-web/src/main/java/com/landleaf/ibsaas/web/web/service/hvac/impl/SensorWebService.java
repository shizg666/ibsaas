package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.hvac.modbus.MbNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbNode;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.sensor.SensorHchoLevelEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.ISensorWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private MbNodeDao mbNodeDao;

    @Value("${bacnet.place.id}")
    private String placeId;

    @Override
    public List<SensorVO> overview() {
//        return redisHandle.getMapField(placeId, String.valueOf(ModbusDeviceTypeEnum.SENSOR.getDeviceType()));
        return (List<SensorVO>) baseOverview(ModbusDeviceTypeEnum.SENSOR.getDeviceType());
    }

    @Override
    public Map<String, Map<String, SensorVO>> totalOverview() {
        List<MbNode> nodes = mbNodeDao.getMbNodes(ModbusDeviceTypeEnum.SENSOR.getDeviceType());
        Map<String, MbNode> map = nodes.stream().collect(Collectors.toMap(MbNode::getId, n -> n));
        //楼层归类
        Map<String, Map<String, SensorVO>> result = new HashMap<>(8);
        List<SensorVO> overview = overview();
        overview.forEach(ov -> {
            ov.setSsHchoLevel(SensorHchoLevelEnum.getLevel(ov.getSsHcho()));
            MbNode temp = map.get(ov.getId());
            result.computeIfAbsent(String.valueOf(temp.getFloor()), k-> new HashMap<>(4));
            result.get(String.valueOf(temp.getFloor())).put(temp.getNodeName(), ov);
        });
        return result;
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
