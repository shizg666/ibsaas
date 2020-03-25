package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.Date;
import java.util.List;


/**
 * @author Lokiy
 * @date 2019/6/14 17:37
 * @description:
 */
public interface IEnergyDataService extends IBaseService<EnergyData> {

    /**
     * 电表数值记录
     * @param date
     */
    List<EnergyData> dataRecord(Date date);


    /**
     * 零数据处理 用于填充断电或其他情况的数据缺失
     * @param date
     */
    void zeroDataRecord(Date date);

    /**
     * 电表数值记录
     * @param date
     */
    List<EnergyData> electricDataRecord(Date date);


    /**
     * 水表数值记录
     * @param date
     */
    List<EnergyData> waterDataRecord(Date date);
}
