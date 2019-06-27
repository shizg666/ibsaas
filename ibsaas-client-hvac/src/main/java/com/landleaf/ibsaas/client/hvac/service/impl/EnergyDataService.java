package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyDataService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.WaterMeterVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyTypeEnum;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/6/14 17:38
 * @description:
 */
@Service
@Slf4j
public class EnergyDataService extends AbstractBaseService<EnergyDataDao, EnergyData> implements IEnergyDataService {

    @Autowired
    private ICommonDeviceService iCommonDeviceService;

    @Autowired
    private DaoAdapter<EnergyData> daoAdapter;

    @Autowired
    private EnergyDataDao energyDataDao;


    @Override
    public List<EnergyData> dataRecord(Date date) {
        List<EnergyData> energyData = electricDataRecord(date);
        List<EnergyData> waterDataRecord = waterDataRecord(date);
        energyData.addAll(waterDataRecord);
        return energyData;
    }

    @Override
    public List<EnergyData> electricDataRecord(Date date) {
        List<ElectricMeterVO> electricMeterVOList = (List<ElectricMeterVO>) iCommonDeviceService.getCurrentData(HvacConstant.ELECTRIC_METER_PORT);
        List<EnergyData> energyDataElectrics = energyDataDao.getRecentlyEnergyData(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType());
        List<EnergyData> result = new ArrayList<>();
        Map<String, BigDecimal> map = energyDataElectrics.stream().collect(Collectors.toMap(EnergyData::getNodeId, EnergyData::getEnergyDataValue));

        electricMeterVOList.forEach( em -> {
            EnergyData record = new EnergyData();
            record.setNodeId(em.getId());
            record.setEnergyDataTime(date);
            record.setEnergyDataDate(date);
            record.setEnergyDataMonth(CalendarUtil.getYearAndMonth(date));
            record.setEnergyDataYear(CalendarUtil.year(date));

            BigDecimal electricDataValue = new BigDecimal(em.getEmReading());
            record.setEnergyDataValue(electricDataValue);
            BigDecimal recentlyValue = map.get(em.getId());
            BigDecimal increaseValue = recentlyValue == null ? BigDecimal.ZERO : electricDataValue.subtract(recentlyValue);
            record.setEnergyDataIncreaseValue(increaseValue.compareTo(BigDecimal.ZERO)<0 ? BigDecimal.ZERO: increaseValue);

            record.setEnergyDataType(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType());
            record.setEnergyDataSource(IbsaasConstant.ENERGY_DATA_SOURCE_1);

            daoAdapter.consummateAddOperation(record);

            EnergyData allreadyData = energyDataDao.getEnergyDataByNodeIdAndTime(record);
            if(allreadyData == null) {
                saveSelective(record);
            }
            result.add(record);
        });

        return result;
    }

    @Override
    public List<EnergyData> waterDataRecord(Date date) {
        List<WaterMeterVO> waterMeterVOList = (List<WaterMeterVO>) iCommonDeviceService.getCurrentData(HvacConstant.WATER_METER_PORT);
        List<EnergyData> energyDataWaters = energyDataDao.getRecentlyEnergyData(EnergyTypeEnum.ENERGY_WATER.getEnergyType());
        Map<String, BigDecimal> map = energyDataWaters.stream().collect(Collectors.toMap(EnergyData::getNodeId, EnergyData::getEnergyDataValue));
        List<EnergyData> result = new ArrayList<>();
        waterMeterVOList.forEach( wm -> {
            EnergyData record = new EnergyData();
            record.setNodeId(wm.getId());
            record.setEnergyDataTime(date);
            record.setEnergyDataDate(date);
            record.setEnergyDataMonth(CalendarUtil.getYearAndMonth(date));
            record.setEnergyDataYear(CalendarUtil.year(date));

            BigDecimal electricDataValue = new BigDecimal(wm.getWmReading());
            record.setEnergyDataValue(electricDataValue);
            BigDecimal recentlyValue = map.get(wm.getId());
            BigDecimal increaseValue = recentlyValue == null ? BigDecimal.ZERO : electricDataValue.subtract(recentlyValue);
            record.setEnergyDataIncreaseValue(increaseValue.compareTo(BigDecimal.ZERO)<0 ? BigDecimal.ZERO: increaseValue);

            record.setEnergyDataType(EnergyTypeEnum.ENERGY_WATER.getEnergyType());
            record.setEnergyDataSource(IbsaasConstant.ENERGY_DATA_SOURCE_1);

            daoAdapter.consummateAddOperation(record);
            EnergyData allreadyData = energyDataDao.getEnergyDataByNodeIdAndTime(record);
            if(allreadyData == null) {
                saveSelective(record);
            }
            result.add(record);
        });
        return result;
    }
}
