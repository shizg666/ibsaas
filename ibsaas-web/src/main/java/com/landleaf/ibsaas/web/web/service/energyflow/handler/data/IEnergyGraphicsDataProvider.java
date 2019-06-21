package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import java.util.Map;

public interface IEnergyGraphicsDataProvider {



    public void buildParam(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime);

    Map<String,Object> getEnergyFlowData(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime);
}
