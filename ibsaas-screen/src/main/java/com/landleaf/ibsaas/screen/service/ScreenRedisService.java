package com.landleaf.ibsaas.screen.service;

import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/12/11 9:25
 * @description:
 */
@Service
public class ScreenRedisService {

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;


    /**
     * 获取多参数的数据
     * @return
     */
    public List<SensorVO> getSensors(){
        Object result = get(ModbusDeviceTypeEnum.SENSOR.getDeviceType());
        return result == null ? new ArrayList<>() : (List<SensorVO>) result;
    }


    /**
     * 获取风机盘管数据
     * @return
     */
    public List<FanCoilVO> getFanCoils(){
        Object result = get(BacnetDeviceTypeEnum.FAN_COIL.getDeviceType());
        return result == null ? new ArrayList<>() : (List<FanCoilVO>) result;
    }


    /**
     * 获取新风机数据
     * @return
     */
    public List<NewFanVO> getNewFans(){
        Object result = get(BacnetDeviceTypeEnum.NEW_FAN.getDeviceType());
        return result == null ? new ArrayList<>() : (List<NewFanVO>) result;
    }


    /**
     * 处理redis失败的情况
     * @param type
     * @return
     */
    private Object get(Integer type){
        try {
            return redisHandle.getMapField(placeId, String.valueOf(type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
