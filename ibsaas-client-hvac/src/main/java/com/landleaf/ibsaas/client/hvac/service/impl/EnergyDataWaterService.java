package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyDataWaterService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataElectricDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataWaterDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.WaterMeterVO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
@Service
@Slf4j
@AllArgsConstructor
public class EnergyDataWaterService extends AbstractBaseService<EnergyDataWaterDao, EnergyDataWater> implements IEnergyDataWaterService {

    private ICommonDeviceService iCommonDeviceService;

    private DaoAdapter<EnergyDataWater> daoAdapter;

    private EnergyDataWaterDao energyDataWaterDao;

    @Override
    public List<EnergyDataWater> dataRecord(Date date) {
        List<WaterMeterVO> waterMeterVOList = (List<WaterMeterVO>) iCommonDeviceService.getCurrentData(HvacConstant.WATER_METER_PORT);
        List<EnergyDataWater> energyDataWaters = energyDataWaterDao.getRecentlyData();
        Map<String, BigDecimal> map = energyDataWaters.stream().collect(Collectors.toMap(EnergyDataWater::getNodeId, EnergyDataWater::getWaterDataValue));
        List<EnergyDataWater> result = new ArrayList<>();
        waterMeterVOList.forEach( wm -> {
            EnergyDataWater record = new EnergyDataWater();
            record.setNodeId(wm.getId());
            record.setWaterDataTime(date);

            BigDecimal waterDataValue = new BigDecimal(wm.getWmReading());
            record.setWaterDataValue(waterDataValue);

            BigDecimal recentlyValue = map.get(wm.getId());
            record.setWaterDataIncreaseValue(recentlyValue == null?BigDecimal.ZERO : waterDataValue.subtract(recentlyValue));

            daoAdapter.consummateAddOperation(record);
            save(record);
            result.add(record);
        });
        return result;
    }
}
