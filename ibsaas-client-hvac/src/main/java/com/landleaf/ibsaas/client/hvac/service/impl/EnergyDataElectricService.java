package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IEnergyDataElectricService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataElectricDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO;
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
public class EnergyDataElectricService extends AbstractBaseService<EnergyDataElectricDao, EnergyDataElectric> implements IEnergyDataElectricService {

    private ICommonDeviceService iCommonDeviceService;

    private DaoAdapter<EnergyDataElectric> daoAdapter;

    private EnergyDataElectricDao energyDataElectricDao;

    @Override
    public List<EnergyDataElectric> dataRecord(Date date){
        List<ElectricMeterVO> electricMeterVOList = (List<ElectricMeterVO>) iCommonDeviceService.getCurrentData(HvacConstant.ELECTRIC_METER_PORT);
        List<EnergyDataElectric> energyDataElectrics = energyDataElectricDao.getRecentlyData();
        List<EnergyDataElectric> result = new ArrayList<>();
        Map<String, BigDecimal> map = energyDataElectrics.stream().collect(Collectors.toMap(EnergyDataElectric::getNodeId, EnergyDataElectric::getElectricDataValue));
        electricMeterVOList.forEach( em -> {
            EnergyDataElectric record = new EnergyDataElectric();
            record.setNodeId(em.getId());
            record.setElectricDataTime(date);

            BigDecimal electricDataValue = new BigDecimal(em.getEmReading());
            record.setElectricDataValue(electricDataValue);

            BigDecimal recentlyValue = map.get(em.getId());
            record.setElectricDataIncreaseValue(recentlyValue == null?BigDecimal.ZERO : electricDataValue.subtract(recentlyValue));

            daoAdapter.consummateAddOperation(record);
            save(record);
            result.add(record);
        });

        return result;
    }
}
