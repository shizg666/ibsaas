package com.landleaf.ibsaas.web.web.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyDataShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName EnergyDataShowListener
 * @Description: TODO
 * @Author shizg
 * @Date 2021/1/21
 * @Version V1.0
 **/
@Slf4j
@Component
public class EnergyDataShowListener {

    @Autowired
    private IEnergyDataShowService iEnergyDataShowService;
    @Autowired
    AsyncEventBus asyncEventBus;

    @Subscribe
    public void energyDataShowEvent(EnergyDataShowEvent event) {
        if (event.getRetryCount() >3){
            log.error("energyDataShowEvent -------------->发了3次依然报错！！！！！！！");
            return;
        }
        event.setRetryCount(event.getRetryCount()+1);
        try{
            iEnergyDataShowService.refreshEnergyDataShow();
        }catch (Exception e){
            log.error("energyDataShowEvent -------------->报错:{}",e.getMessage());
            asyncEventBus.post(event);
        }

    }
}
