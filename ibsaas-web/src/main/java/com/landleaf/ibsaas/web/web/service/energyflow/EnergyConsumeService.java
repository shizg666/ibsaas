package com.landleaf.ibsaas.web.web.service.energyflow;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.IEnergyGraphicsDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EnergyConsumeService implements IEnergyConsumeService {


    @Autowired
    private IEnergyGraphicsDataProvider energyGraphicsDataProvider;

    @Override
    public Map<String, Map<String, List<String>>> energyFlow(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime) {
        Map<String, Map<String, List<String>>> result = Maps.newHashMap();

        energyGraphicsDataProvider.buildParam(queryType, queryValue, dateType, equipType, startTime, endTime);
        energyGraphicsDataProvider.getOrginData();

        return result;
    }
}
