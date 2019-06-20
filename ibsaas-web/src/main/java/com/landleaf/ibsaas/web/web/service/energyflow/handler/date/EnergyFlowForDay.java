package com.landleaf.ibsaas.web.web.service.energyflow.handler.date;

import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.web.web.service.energyflow.AbstractEnergyEnergyFlowHandler;
import com.landleaf.ibsaas.web.web.service.energyflow.EnergyFlowAnnoation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * {@link DimensionTypeEnum}
 */
@EnergyFlowAnnoation(code = "day")
@Component
public class EnergyFlowForDay extends AbstractEnergyEnergyFlowHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(EnergyFlowForDay.class);


    @Override
    public Map<String, List<String>> handle() {


        Map<String, List<EnergyReportResponseVO>> orginData = this.orginData;


        return null;
    }


}
