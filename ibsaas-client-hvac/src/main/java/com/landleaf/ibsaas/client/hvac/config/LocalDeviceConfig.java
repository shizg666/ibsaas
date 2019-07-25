package com.landleaf.ibsaas.client.hvac.config;

import com.landleaf.ibsaas.client.hvac.constant.BacnetConstant;
import com.landleaf.ibsaas.client.hvac.service.IHvacDeviceService;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.enums.hvac.BaTypeEnum;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkUtils;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/20 9:07
 * @description: BACnet本地设备数据配置
 */
@Component
@Slf4j
public class LocalDeviceConfig {

    /**
     *  配置本地设备
     */
    private static LocalDevice localDevice;

    @Autowired
    private HvacDeviceDao hvacDeviceDao;

    /**
     * 设置本地设备的设备id
     */
    @Value("${bacnet.local.deviceId}")
    private int localDeviceId;

    /**
     * 初始化本地设备
     */
    @PostConstruct
    public void initLocalDevice(){
        DefaultTransport transport = new DefaultTransport(
                new IpNetworkBuilder()
                        .broadcastIp(IpNetwork.DEFAULT_BROADCAST_IP)
                        .port(IpNetwork.DEFAULT_PORT)
                        .build());
        List<HvacDevice> hvacDevices = hvacDeviceDao.groupByNetwork();
        hvacDevices.forEach(hd -> transport.addNetworkRouter(hd.getNetworkNumber(), IpNetworkUtils.toOctetString(hd.getIp() + BacnetConstant.COLON + hd.getPort())));
        localDevice = new LocalDevice(localDeviceId, transport);
        try {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>本地设备开始初始化<<<<<<<<<<<<<<<<<<<<<<<<<");
            localDevice.initialize();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>本地设备初始化成功<<<<<<<<<<<<<<<<<<<<<<<<<");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>本地设备初始化失败:"+e.getMessage(),e);
        }
    }

    /**
     * 获取本地设备
     * @return
     */
    public static LocalDevice getLocalDevice(){
        return localDevice;
    }

    /**
     * 销毁本地设备
     */
    @PreDestroy
    public void destroyLocalDevice(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>本地设备开始销毁<<<<<<<<<<<<<<<<<<<<<<<<<");
        localDevice.terminate();
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>本地设备销毁结束<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
