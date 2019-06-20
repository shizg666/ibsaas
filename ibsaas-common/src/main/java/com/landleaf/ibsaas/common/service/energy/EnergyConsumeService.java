package com.landleaf.ibsaas.common.service.energy;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EnergyConsumeService implements IEnergyConsumeService {


    @Override
    public Map<String, Map<String, List<String>>> energyFlow(Integer queryType, String queryTypeValue, Integer dimensionType, Integer dataType, String startTime, String endTime) {
        Map<String, Map<String, List<String>>> result = Maps.newHashMap();
        /************************************************************************************************************/
//        DimensionTypeEnum typeEnum = DimensionTypeEnum.getInstByType(dimensionType == null ? DimensionTypeEnum.HOUR.type : dimensionType.intValue());
//        FlowHandler flowHandler = flowHandlerSelector.selectHandler(typeEnum.getCode());
//        dto.setAddressid(tmpAddressId);
//        Map<String, List<String>> tmpMap = flowHandler.handle(dto, tmpAddressId, AddressTypeEnum.ACTUAL_TEMPERATURE.getType());
//        dto.setAddressid(humAddressId);
//        Map<String, List<String>> humMap = flowHandler.handle(dto, humAddressId, AddressTypeEnum.HUMIDITY.getType());
//        result.put(AddressTypeEnum.ACTUAL_TEMPERATURE.type + "", tmpMap);
//        result.put(AddressTypeEnum.HUMIDITY.type + "", humMap);
        return result;
    }
}
