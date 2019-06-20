package com.landleaf.ibsaas.web.web.service.energyflow;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EnergyConsumeService implements IEnergyConsumeService {


    @Autowired
    private EnergyFlowHandlerSelector energyFlowHandlerSelector;

    @Override
    public Map<String, Map<String, List<String>>> energyFlow(Integer equipArea, Integer equipClassification, Integer dateType, Integer equipType, String startTime, String endTime) {
        Map<String, Map<String, List<String>>> result = Maps.newHashMap();
        DimensionTypeEnum typeEnum = DimensionTypeEnum.getInstByType(dateType == null ? DimensionTypeEnum.HOUR.type : dateType.intValue());
        EnergyFlowHandler energyFlowHandler = energyFlowHandlerSelector.selectHandler(typeEnum.getCode());
        energyFlowHandler.buildParam(equipArea, equipClassification, dateType, equipType, startTime, endTime);
        Map<String, List<String>> tmpMap = energyFlowHandler.handle();
        return result;
    }
}
