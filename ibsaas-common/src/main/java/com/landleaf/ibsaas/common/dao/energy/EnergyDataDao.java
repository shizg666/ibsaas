package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.energy.HlVlBO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.domain.energy.report.DayOfYear;
import com.landleaf.ibsaas.common.domain.energy.report.IntervalData;
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
    List<EnergyData> getRecentlyEnergyData(@Param("energyType") Integer energyType);

    /**
     * 根据能耗类型获取上条最新信息 默认定时任务
     * @param energyType
     * @return
     */
    List<EnergyData> getRecentlyEnergyDataByTime(@Param("energyType") Integer energyType, @Param("inTime") Date inTime);

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
    List<IntervalData> getEnergyDateByTime(@Param("equipType") Integer equipType);

    /**
     * 根据年份获取日
     * @param year
     * @return
     */
    List<DayOfYear> getDaysByYear(@Param("year") Integer year);


    /**
     * 根据nodeId和时间 类型 查询 是否存在相同数据
     * @param energyData
     * @return
     */
    EnergyData getEnergyDataByNodeIdAndTime(EnergyData energyData);


    /**
     * 获取数据最早的时间
     * @return
     */
    Date getFirstDate();

    /**
     * 根据时间获取电表抄表数据
     * @param datetime
     * @return
     */
    BigDecimal getSumElectricByDate(@Param("datetime") Date datetime);
}
