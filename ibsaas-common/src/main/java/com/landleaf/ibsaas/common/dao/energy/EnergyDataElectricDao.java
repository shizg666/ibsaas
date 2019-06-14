package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataElectric;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyDataElectricDao extends BaseDao<EnergyDataElectric> {
    /**
     * 获取上次抄表时的数据
     * @return
     */
    List<EnergyDataElectric> getRecentlyData();

}
