package com.landleaf.ibsaas.web.web.service.energyflow;

import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.IEnergyGraphicsDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class EnergyConsumeService implements IEnergyConsumeService {


    @Autowired
    private IEnergyGraphicsDataProvider energyGraphicsDataProvider;

    @Override
    public Map<String, Object> energyFlow(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes) {


        Map<String,Object> data=energyGraphicsDataProvider.getEnergyFlowData(queryType, queryValue, dateType, equipType, startTime, endTime,chartTypes);
        return data;
    }

    @Override
    public Object energyFlowForOne(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime, EnergyGraphicsEnum chartType) {
        List<EnergyGraphicsEnum> chartTypes = CollectionUtils.arrayToList(new EnergyGraphicsEnum[]{chartType});
        Map<String, Object> result=energyFlow(queryType,queryValue,dateType,equipType,startTime,endTime,chartTypes );
        return result.get(chartType.getCode());
    }
}
