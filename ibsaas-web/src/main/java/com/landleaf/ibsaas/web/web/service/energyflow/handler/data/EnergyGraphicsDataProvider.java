package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnergyGraphicsDataProvider {

    @Autowired
    private IEnergyReportService energyReportService;
    /**
     * 能耗时间拆线图
     *
     * @param requestBody
     * @return
     */
    public  List<EnergyReportResponseVO> timeLineChart(EnergyReportDTO requestBody) {

        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);

        return energyReporyInfolist;

    }



}
