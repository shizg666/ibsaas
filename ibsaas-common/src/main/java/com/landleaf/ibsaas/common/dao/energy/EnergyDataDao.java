package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.energy.HlVlBO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.domain.energy.report.TimeEnergyData;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
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
    List<HlVlBO> overviewLineChart(EnergyReportExDTO energyReportDTO);

    /**
     * 根据不同维度获取能耗数值
     * @param startTime
     * @param endTime
     * @param equipType
     * @param energyConfigSetting
     * @param lmt
     * @return
     */
    List<HlVlBO> getEnergyRanking(@Param("startTime") Date startTime,
                                  @Param("endTime") Date endTime,
                                  @Param("equipType") Integer equipType,
                                  @Param("energyConfigSetting") String energyConfigSetting,
                                  @Param("lmt") Integer lmt);

    /**
     * 根据时间段获取总值
     * @param energyReportDTO
     * @return
     */
    BigDecimal getEnergyByDate(EnergyReportExDTO energyReportDTO);

    /**
     * 分项域报表统计
     * @param queryVO
     * @return
     */
    List<EnergyReportResponseVO> getEnergyReporyInfoByType(EnergyReportQueryVO queryVO);


    List<EnergyReportResponseVO> getEnergyReporyInfoByArea(EnergyReportQueryVO queryVO);

    /**
     * 按类型和年统计能耗
     * @param equipType
     * @param year
     * @return
     */
    BigDecimal getEnergyByYear(@Param("equipType") Integer equipType,
                               @Param("year") Integer year);


    /**
     * 根据时间获取能耗
     * @param equipType
     * @return
     */
    List<TimeEnergyData> getEnergyDateByTime( Integer equipType);

}
