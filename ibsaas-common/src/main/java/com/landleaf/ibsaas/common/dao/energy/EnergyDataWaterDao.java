package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataWater;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyDataWaterDao extends BaseDao<EnergyDataWater> {

    /**
     * 获取上次的值
     * @return
     */
    List<EnergyDataWater> getRecentlyData();
}
