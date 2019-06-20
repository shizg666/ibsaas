package com.landleaf.ibsaas.common.service.energy;

import java.util.List;
import java.util.Map;

public interface IEnergyConsumeService {

    /**
     *
     * @param queryType  查询类型（分区/分项）
     * @param queryTypeValue 分区值/分项值
     * @param dimensionType   维度
     * @param dataType         数据类型（电/水）
     * @param startTime        起始时间
     * @param endTime          截止时间
     * @return
     */
    Map<String, Map<String, List<String>>> energyFlow(Integer queryType, String queryTypeValue, Integer dimensionType, Integer dataType, String startTime, String endTime);
}
