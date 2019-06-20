package com.landleaf.ibsaas.web.web.service.energyflow;


import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;

import java.util.List;
import java.util.Map;

public interface EnergyFlowHandler {

	/**
	 * 查询能耗曲线
	 * @return
	 */
	Map<String,List<String>> handle();

	/**
	 * 生成查询参数
	 * @param equipArea
	 * @param equipClassification
	 * @param dateType
	 * @param equipType
	 * @param startTime
	 * @param endTime
	 */
	void buildParam(Integer equipArea, Integer equipClassification, Integer dateType, Integer equipType, String startTime, String endTime);


	/**
	 * 根据DateType转换为相应起止时间
	 * @param requestBody
	 */
	public void convertimeByDateType(EnergyReportDTO requestBody);
}
