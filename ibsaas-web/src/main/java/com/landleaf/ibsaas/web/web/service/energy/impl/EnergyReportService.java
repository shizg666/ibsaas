package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataDao;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyOverviewTotalVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/18 16:03
 * @description:
 */
@Service
@Slf4j
public class EnergyReportService implements IEnergyReportService {


    @Autowired
    private EnergyDataDao energyDataDao;


    @Override
    public HlVl overviewLineChart(EnergyReportDTO energyReportDTO) {
        return energyDataDao.overviewLineChart(energyReportDTO);
    }

    @Override
    public HlVl overviewHistogram(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl overviewSavingEffect(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl overviewRankingClassification(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl overviewRankingArea(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl overviewYoy(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl overviewQoq(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public EnergyOverviewTotalVO overviewTotal() {
        return null;
    }

    @Override
    public HlVl areaLineChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaHistogram(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaPieChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaYoy(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaQoq(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationLineChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationHistogram(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationPieChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationYoy(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationQoq(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public List<EnergyReportResponseVO> getEnergyReporyInfolist(EnergyReportDTO queryVO) {
        List<EnergyReportResponseVO> responseVOS = Lists.newArrayList();
        DimensionTypeEnum dimensionTypeEnum = DimensionTypeEnum.getInstByType(queryVO.getDateType());
        String columValue = dimensionTypeEnum.code;
        queryVO.setDateTypeValue(columValue);
        if (queryVO.getEquipArea() == null){
            responseVOS = getEnergyReporyInfoByArea(queryVO);
        }else {
            responseVOS = getEnergyReporyInfoByType(queryVO);
        }
        return responseVOS;
    }

    private List<EnergyReportResponseVO> getEnergyReporyInfoByArea(EnergyReportDTO queryVO) {
        return energyDataDao.getEnergyReporyInfoByType(queryVO);
    }
    private List<EnergyReportResponseVO> getEnergyReporyInfoByType(EnergyReportDTO queryVO) {
        return energyDataDao.getEnergyReporyInfoByType(queryVO);
    }


    public List<Integer> getEnergyIDlist(EnergyReportDTO queryVO) {
        return energyDataDao.getEnergyReporyInfoByType(queryVO);
    }
}
