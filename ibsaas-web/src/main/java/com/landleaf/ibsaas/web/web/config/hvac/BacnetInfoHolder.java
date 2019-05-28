package com.landleaf.ibsaas.web.web.config.hvac;


import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.web.web.constant.BacnetConstant;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacDeviceService;
import com.landleaf.ibsaas.web.web.util.BacnetUtil;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkUtils;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/5/21 16:43
 * @description: Bacc相关信息容器
 */
@Configuration
@Slf4j
@DependsOn("localDeviceConfig")
public class BacnetInfoHolder {

    public static final ConcurrentHashMap<Integer, RemoteDevice> REMOTE_DEVICE_MAP = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, List<ObjectIdentifier>> OID_MAP = new ConcurrentHashMap<>();
//    public static final ConcurrentHashMap<Integer, List<ObjectIdentifier>> OID_ANALOGINPUT_MAP = new ConcurrentHashMap<>();

    @Autowired
    private IHvacDeviceService iHvacDeviceService;

    @PostConstruct
    public void init(){
        List<HvacDevice> hvacDevices = iHvacDeviceService.all();
        LocalDevice localDevice = LocalDeviceConfig.getLocalDevice();
        hvacDevices.forEach(brd ->{
            try {
                RemoteDevice remoteDevice = localDevice.findRemoteDevice(
                        new Address(IpNetworkUtils.toOctetString(brd.getIp() + BacnetConstant.COLON + brd.getPort())),
                        brd.getDeviceInstanceNumber());
                List<ObjectIdentifier> objectIdentifiers = BacnetUtil.getAllRemoteObjectIdentifier(localDevice, remoteDevice);
//                List<ObjectIdentifier> analogInputList = objectIdentifiers.stream().filter(oid -> ObjectType.analogInput.equals(oid.getObjectType())).collect(Collectors.toList());

                REMOTE_DEVICE_MAP.put(brd.getDeviceInstanceNumber(), remoteDevice);
                OID_MAP.put(brd.getDeviceInstanceNumber(), objectIdentifiers);
//                OID_ANALOGINPUT_MAP.put(brd.getDeviceInstanceNumber(), analogInputList);
            } catch (BACnetException e) {
                e.printStackTrace();
                log.error("------------------------------>连接远程设备发生异常:"+e.getMessage(), e);
            }
        });
    }


    /**
     * 清空容器缓存
     */
    private void clear() {
        REMOTE_DEVICE_MAP.clear();
        OID_MAP.clear();
    }

    /**
     * 重写加载设备和点位
     */
    public void reload(){
        clear();
        init();
    }
}
