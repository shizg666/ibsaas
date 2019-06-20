package com.landleaf.ibsaas.web.web.service.energyflow.handler.data.asyn;

import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.EnergyGraphicsMsgProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class EnergyFutureService implements IEnergyFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(EnergyFutureService.class);
    @Autowired
    private EnergyGraphicsMsgProcessor energyGraphicsMsgProcessor;

    /**
     * 异步处理消息
     *
     */
    @Async
    public Future handlerMsg(EnergyGraphicsEnum energyGraphicsEnum, Object requestBody) {
        Future<List<EnergyReportResponseVO>> future = null;
        try {
            //处理消息
            List<EnergyReportResponseVO> result=energyGraphicsMsgProcessor.process(energyGraphicsEnum,requestBody);
            future = (Future<List<EnergyReportResponseVO>>) new AsyncResult<List<EnergyReportResponseVO>>(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }

}
