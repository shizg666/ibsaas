package com.landleaf.ibsaas.web.web.service.energy;

import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyOverviewTotalVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/18 16:02
 * @description:
 */
public interface IEnergyReportService {

    /**
     * 能耗总览-折线图
     * @param energyReportDTO
     * @return
     */
    HlVl overviewLineChart(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-柱状图
     * @param energyReportDTO
     * @return
     */
    HlVl overviewHistogram(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-节能效果
     * @param energyReportDTO
     * @return
     */
    HlVl overviewSavingEffect(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-能耗排行TOP5项
     * @param energyReportDTO
     * @return
     */
    HlVl overviewRankingClassification(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-能耗排行TOP3区
     * @param energyReportDTO
     * @return
     */
    HlVl overviewRankingArea(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-同比
     * @param energyReportDTO
     * @return
     */
    String overviewYoy(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-环比
     * @param energyReportDTO
     * @return
     */
    String overviewQoq(EnergyReportExDTO energyReportDTO);

    /**
     * 能耗总览-累计能耗
     * @return
     */
    EnergyOverviewTotalVO overviewTotal();


    /**
     * 区域能耗-折线图
     * @param energyReportDTO
     * @return
     */
    HlVl areaLineChart(EnergyReportDTO energyReportDTO);

    /**
     * 区域能耗-柱状图
     * @param energyReportDTO
     * @return
     */
    HlVl areaHistogram(EnergyReportDTO energyReportDTO);

    /**
     * 区域能耗-柱状图
     * @param energyReportDTO
     * @return
     */
    HlVl areaPieChart(EnergyReportDTO energyReportDTO);

    /**
     * 区域能耗-同比
     * @param energyReportDTO
     * @return
     */
    HlVl areaYoy(EnergyReportDTO energyReportDTO);

    /**
     * 区域能耗-环比
     * @param energyReportDTO
     * @return
     */
    HlVl areaQoq(EnergyReportDTO energyReportDTO);


    /**
     * 分项能耗-折线图
     * @param energyReportDTO
     * @return
     */
    HlVl classificationLineChart(EnergyReportDTO energyReportDTO);

    /**
     * 分项能耗-柱状图
     * @param energyReportDTO
     * @return
     */
    HlVl classificationHistogram(EnergyReportDTO energyReportDTO);

    /**
     * 分项能耗-柱状图
     * @param energyReportDTO
     * @return
     */
    HlVl classificationPieChart(EnergyReportDTO energyReportDTO);

    /**
     * 分项能耗-同比
     * @param energyReportDTO
     * @return
     */
    HlVl classificationYoy(EnergyReportDTO energyReportDTO);

    /**
     * 分项能耗-环比
     * @param energyReportDTO
     * @return
     */
    HlVl classificationQoq(EnergyReportDTO energyReportDTO);


    /**
     * 分项分区域报表统计
     * @param queryVO
     * @return Report
     */
    List<EnergyReportResponseVO> getEnergyReporyInfolist(EnergyReportQueryVO queryVO);
}
