package com.landleaf.ibsaas.client.hvac.config;

import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;

import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
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
 * @date 2019/7/2 16:52
 * @description: 设备节点初始化容器,避免服务器频繁创建节点对象
 */
@Component
@Slf4j
public class HvacNodeHolder {

    // 类型下所有设备
    public static final Map<Integer, List<BaseDevice>> DEVICE_NODE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @PostConstruct
    public void init(){
        List<HvacNode> hvacNodes = hvacNodeDao.all();
        hvacNodes.forEach(hn -> {
            DEVICE_NODE_MAP.computeIfAbsent(hn.getDeviceType(), k -> new ArrayList<>());
            BaseDevice device = getByDeviceType(hn.getDeviceType());
            device.setId(hn.getId());
            DEVICE_NODE_MAP.get(hn.getDeviceType()).add(device);
        });
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>HvacNodeHolder.init初始化完成<<<<<<<<<<<<<<<<<<<<<<<<<");
    }



    /**
     * 存入的设备
     * @param deviceType
     * @return
     */
    private BaseDevice getByDeviceType(Integer deviceType){
        BacnetDeviceTypeEnum bacnetDeviceTypeEnum = BacnetDeviceTypeEnum.getBacnetDeviceTypeEnum(deviceType);
        if(bacnetDeviceTypeEnum == null){
            return new BaseDevice();
        }
        try {
            Class<?> aClass = Class.forName(bacnetDeviceTypeEnum.getClassPath());
            return (BaseDevice) aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>HvacNodeHolder.getByDeviceType类类型生成错误:{}",e.getMessage(), e);
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
