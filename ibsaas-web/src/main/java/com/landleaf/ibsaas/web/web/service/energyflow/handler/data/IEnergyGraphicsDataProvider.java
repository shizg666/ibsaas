package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;

import java.util.List;
import java.util.Map;

public interface IEnergyGraphicsDataProvider {



    public EnergyReportQueryVO buildParam(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime, List<EnergyGraphicsEnum> chartTypes);

    Map<String,Object> getEnergyFlowData(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes);
}
