package com.landleaf.ibsaas.web.web.service.energyflow;

import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;

import java.util.List;
import java.util.Map;

public interface IEnergyConsumeService {
    /**
     *
     * @param queryType  查询类型
     * @param queryValue 查询类型值
     * @param dateType   维度
     * @param equipType         数据类型（电/水）
     * @param startTime        起始时间
     * @param endTime          截止时间
     * @param chartTypes        查询图类型
     * @return
     */
    Map<String,Object> energyFlow(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes);

    Object energyFlowForOne(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,EnergyGraphicsEnum chartType);


}
