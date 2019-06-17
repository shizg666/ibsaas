package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.IEnergyEquipDataService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDataDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipData;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyDataValueDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
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
public class EnergyEquipDataService extends AbstractBaseService<EnergyEquipDataDao, EnergyEquipData> implements IEnergyEquipDataService {

    @Autowired
    private EnergyEquipDao energyEquipDao;

    @Autowired
    private DaoAdapter<EnergyEquipData> dataDaoAdapter;

    @Override
    public List<EnergyEquipData> dateRecord(Date now, List<EnergyDataElectric> energyDataElectrics, List<EnergyDataWater> energyDataWaters) {
        Map<String, EnergyDataValueDTO> map = energyDataElectrics.stream().collect(Collectors.toMap(EnergyDataElectric::getNodeId, ede -> new EnergyDataValueDTO(ede.getElectricDataValue(), ede.getElectricDataIncreaseValue())));
        Map<String, EnergyDataValueDTO> waterMap = energyDataWaters.stream().collect(Collectors.toMap(EnergyDataWater::getNodeId, edw -> new EnergyDataValueDTO(edw.getWaterDataValue(), edw.getWaterDataIncreaseValue())));
        map.putAll(waterMap);
        //查询所有的设备
        List<EnergyEquipVO> energyEquipVOList = energyEquipDao.allWithNodeIds();
        List<EnergyEquipData> result = new ArrayList<>();
        energyEquipVOList.forEach( ee -> {
            EnergyEquipData equipData = new EnergyEquipData();
            equipData.setEquipId(ee.getId());
            equipData.setDataTime(now);
            BigDecimal dataValue = BigDecimal.ZERO;
            BigDecimal increaseDataValue = BigDecimal.ZERO;
            List<HvacNodeVO> nodes = ee.getNodes();
            for(HvacNodeVO n : nodes){
                dataValue = dataValue.add(map.get(n.getNodeId()).getDataValue());
                increaseDataValue = increaseDataValue.add(map.get(n.getNodeId()).getIncreaseDataValue());
            }
            equipData.setDataValue(dataValue);
            equipData.setDataIncreaseValue(increaseDataValue);
            dataDaoAdapter.consummateAddOperation(equipData);
            saveSelective(equipData);
            result.add(equipData);
        });

        return result;
    }
}
