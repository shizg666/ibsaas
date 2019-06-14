package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.Date;


/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
public interface IEnergyDataWaterService extends IBaseService<EnergyDataWater> {


    /**
     * 水表数值记录
     * @param date
     */
    void dataRecord(Date date);
}
