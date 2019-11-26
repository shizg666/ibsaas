package com.landleaf.ibsaas.client.light.init;

import com.landleaf.ibsaas.client.light.handle.light.ScencesMonitorHandler;
import com.landleaf.ibsaas.client.light.service.ITLightDeviceService;
import com.landleaf.ibsaas.client.light.service.LightService;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceFloorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时给每个区域的值改变时添加监听
 */
@Component
@Slf4j
public class MyApplicationRunner implements CommandLineRunner {

    @Autowired
    private LightService lightService;
    @Autowired
    private ITLightDeviceService itLightDeviceService;
    @Autowired
    private ScencesMonitorHandler scencesMonitorHandler;

    @Override
    public void run(String... args) throws Exception {
//        List<TLightDevice> tLightDeviceList = itLightDeviceService.getDeviceList();
        List<LightDeviceFloorVO> tLightDeviceList = itLightDeviceService.deviceAutoMonitor();
        tLightDeviceList.forEach(obj->{
            LightMsg lightMsg = new LightMsg();
            lightMsg.setAdress(obj.getAdress());
            lightMsg.setValue("1");
            lightMsg.setFloor(String.valueOf(obj.getFloor()));
            lightMsg.setType("2");
            scencesMonitorHandler.process(lightMsg);
//            //手动拉取
//            LightMsg lightMsg2 = new LightMsg();
//            lightMsg2.setAdress(obj.getAdress());
//            lightMsg2.setFloor(String.valueOf(obj.getFloor()));
//            lightMsg2.setType("3");
//            scencesMonitorHandler.process(lightMsg);
        });
    }
}




