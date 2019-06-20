package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyDataDao extends BaseDao<EnergyData> {

    /**
     * 根据能耗类型获取上条最新信息 默认定时任务
     * @param energyType
     * @return
     */
    List<EnergyData> getRecentlyEnergyData(Integer energyType);

    /**
     * 能耗总览-折线图
     * 
     * @param energyReportDTO
     * @return
     */
    HlVl overviewLineChart(EnergyReportDTO energyReportDTO);
}
