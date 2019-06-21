package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.asyn.IEnergyFutureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class EnergyGraphicsDataProvider extends AbstractEnergyGraphicsDataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyGraphicsDataProvider.class);
    @Autowired
    private IEnergyFutureService energyFutureService;

    @Override
    public Map<String, Object> getEnergyFlowData(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes) {
        buildParam(queryType, queryValue, dateType, equipType, startTime, endTime,chartTypes);
        getEnergyFlowData();
        return this.data;
    }

    public void getEnergyFlowData(){
        Map<String ,Object> data = Maps.newHashMap();
        LOGGER.info("*************************逐个获取图表数据*****************************");
        long getDateStartTime = System.currentTimeMillis();
        Map<String, Future> futureMap = Maps.newHashMap();
        for (EnergyGraphicsEnum enumObj : this.chartTypes) {
            Future<Object> future = energyFutureService.handlerMsg(enumObj, this.reportQueryVO);
            futureMap.put(enumObj.getCode(),future);
        }
        futureMap.forEach((i,v)->{
            Object o = null;
            try {
                 o =  v.get();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(),e);
            } catch (ExecutionException e) {
                LOGGER.error(e.getMessage(),e);
            }
            data.put(i,o);
        });
        LOGGER.info("*************************共计耗时:{}毫秒*****************************",(System.currentTimeMillis()-getDateStartTime));
        this.data= data;
    }



}
