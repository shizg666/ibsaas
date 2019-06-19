package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipData;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/14 17:37
 * @description:
 */
public interface IEnergyEquipDataCliService extends IBaseService<EnergyEquipData> {
    /**
     * 根据水电表的入库情况获取设备的能耗
     * @param now
     * @param energyDataElectrics
     * @param energyDataWaters
     * @return
     */
    List<EnergyEquipData> dateRecord(Date now, List<EnergyDataElectric> energyDataElectrics, List<EnergyDataWater> energyDataWaters);
}
