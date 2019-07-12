package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.AchpDetailDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpMonitorDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.AchpPumpValveDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpDetailVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpMonitorVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AchpPumpValveVO;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/7/4 10:01
 * @description:
 */
public interface IAchpDetailWebService {

    /**
     * 风冷热泵-详参总览
     * @return
     */
    List<AchpDetailVO> overviewDetail();


    /**
     * 区分高低区
     * @return
     */
    Map<String, List<AchpDetailVO>> totalOverviewDetail();

    /**
     * 修改风冷热泵-详参
     * @param achpDetailDTO
     */
    void updateDetail(AchpDetailDTO achpDetailDTO);


    /**
     * 风冷热泵-水泵水阀总览
     * @return
     */
    List<AchpPumpValveVO> overviewPumpValve();


    /**
     * 修改风冷热泵-水泵水阀
     * @param achpPumpValveDTO
     */
    void updatePumpValve(AchpPumpValveDTO achpPumpValveDTO);


    /**
     * 风冷热泵-监测总览
     * @return
     */
    List<AchpMonitorVO> overviewMonitor();


    /**
     * 修改风冷热泵-监测
     * @param achpMonitorDTO
     */
    void updateMonitor(AchpMonitorDTO achpMonitorDTO);

}
