package com.landleaf.ibsaas.client.hvac.config;


import com.landleaf.ibsaas.client.hvac.constant.BacnetConstant;
import com.landleaf.ibsaas.client.hvac.service.IHvacDeviceService;
import com.landleaf.ibsaas.client.hvac.util.BacnetUtil;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.enums.hvac.BaTypeEnum;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkUtils;
import com.serotonin.bacnet4j.npdu.mstp.MstpNetworkUtils;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/5/21 16:43
 * @description: Bacc相关信息容器
 */
@Configuration
@Slf4j
@DependsOn("localDeviceConfig")
public class RemoteDeviceInfoHolder {

    public static final Map<Integer, RemoteDevice> REMOTE_DEVICE_MAP = new ConcurrentHashMap<>();
    public static final Map<String, RemoteDevice> REMOTE_DEVICE_ID_MAP = new ConcurrentHashMap<>();
//    public static final Map<Integer, List<ObjectIdentifier>> OID_MAP = new ConcurrentHashMap<>();
//    public static final ConcurrentHashMap<Integer, List<ObjectIdentifier>> OID_ANALOGINPUT_MAP = new ConcurrentHashMap<>();

    @Autowired
    private IHvacDeviceService iHvacDeviceService;

    @PostConstruct
    public void init(){
        List<HvacDevice> hvacDevices = iHvacDeviceService.all(null);
        LocalDevice localDevice = LocalDeviceConfig.getLocalDevice();
        hvacDevices.forEach(brd ->{
            try {
                RemoteDevice remoteDevice = null;
                if(BaTypeEnum.BA_GATEWAY.getType().equals(brd.getType())) {

                    remoteDevice = localDevice.findRemoteDevice(
                            new Address(IpNetworkUtils.toOctetString(brd.getIp() + BacnetConstant.COLON + brd.getPort())),
                            brd.getDeviceInstanceNumber());
//                    List<ObjectIdentifier> objectIdentifiers = BacnetUtil.getAllRemoteObjectIdentifier(localDevice, remoteDevice);
                }else if(BaTypeEnum.BA_NETWORK_CONTROLLER.getType().equals(brd.getType())){
                    remoteDevice = localDevice.findRemoteDevice(
                            MstpNetworkUtils.toAddress(brd.getNetworkNumber(), brd.getStation()), brd.getDeviceInstanceNumber());
                }
                REMOTE_DEVICE_MAP.put(brd.getDeviceInstanceNumber(), remoteDevice);
                REMOTE_DEVICE_ID_MAP.put(brd.getId(), remoteDevice);
//                OID_MAP.put(brd.getDeviceInstanceNumber(), objectIdentifiers);
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
//        OID_MAP.clear();
    }

    /**
     * 重写加载设备和点位
     */
    public void reload(){
//        clear();
        init();
    }
}
