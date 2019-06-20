package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;

import java.util.List;

public interface IEnergyGraphicsDataProvider {


    List<String> getDateList(EnergyReportQueryVO requestBody);


    void getOrginData();


    public void buildParam(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime);

}
