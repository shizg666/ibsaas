package com.landleaf.ibsaas.web.web.service.energyflow.handler.data.asyn;

import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface IEnergyFutureService {

    @Async
    Future<List<EnergyReportResponseVO>> handlerMsg(EnergyGraphicsEnum energyGraphicsEnum, Object requestBody);
}
