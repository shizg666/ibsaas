package com.landleaf.ibsaas.web.web.service.energyflow;

import java.util.List;
import java.util.Map;

public interface IEnergyConsumeService {
    /**
     *
     * @param equipArea  所属区域
     * @param equipClassification 所属类型
     * @param dateType   维度
     * @param equipType         数据类型（电/水）
     * @param startTime        起始时间
     * @param endTime          截止时间
     * @return
     */
    Map<String,Map<String,List<String>>> energyFlow(Integer equipArea, Integer equipClassification, Integer dateType, Integer equipType, String startTime, String endTime);


}
