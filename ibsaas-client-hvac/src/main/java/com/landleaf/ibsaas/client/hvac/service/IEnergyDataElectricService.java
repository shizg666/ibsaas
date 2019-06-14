package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.Date;


/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
public interface IEnergyDataElectricService extends IBaseService<EnergyDataElectric> {

    /**
     * 电表数值记录
     * @param date
     */
    void dataRecord(Date date);
}
