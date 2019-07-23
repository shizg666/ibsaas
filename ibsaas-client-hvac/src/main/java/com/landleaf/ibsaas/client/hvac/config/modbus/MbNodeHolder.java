package com.landleaf.ibsaas.client.hvac.config.modbus;

import com.landleaf.ibsaas.common.dao.hvac.modbus.MbNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbNode;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/7/5 17:26
 * @description:
 */
@Component
@Slf4j
public class MbNodeHolder {

    public static final Map<Integer, List<BaseDevice>> MODBUS_NODE_MAP = new ConcurrentHashMap<>();


    @Autowired
    private MbNodeDao mbNodeDao;

    @PostConstruct
    public void init(){
        List<MbNode> list = mbNodeDao.all();
        list.forEach(node -> {
            MODBUS_NODE_MAP.computeIfAbsent(node.getMbType(), k -> new ArrayList<>());
            BaseDevice device = getByDeviceId(node.getMbType());
            device.setId(node.getId());
            MODBUS_NODE_MAP.get(node.getMbType()).add(device);
        });
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>HvacNodeHolder.init初始化完成<<<<<<<<<<<<<<<<<<<<<<<<<");
    }



    /**
     * 存入的设备
     * @param deviceType
     * @return
     */
    private BaseDevice getByDeviceId(Integer deviceType){
        ModbusDeviceTypeEnum typeEnum = ModbusDeviceTypeEnum.getModbusDeviceTypeEnum(deviceType);
        if(typeEnum == null){
            return new BaseDevice();
        }
        try {
            Class<?> aClass = Class.forName(typeEnum.getClassPath());
            return (BaseDevice) aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>MbNodeHolder.getByDeviceId类类型生成错误:{}",e.getMessage(), e);
        }
        return new BaseDevice();
    }


    /**
     * 重新加载
     */
    public void reload(){
        init();
    }
}
