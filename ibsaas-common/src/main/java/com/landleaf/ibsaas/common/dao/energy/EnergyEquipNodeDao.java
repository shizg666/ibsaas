package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquipNode;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyEquipNodeDao extends BaseDao<EnergyEquipNode> {

    /**
     * 根据设备id更新绑定的节点id
     * @param equipId
     * @param userCode
     * @param now
     * @return
     */
    int updateUnActiveByEquipId(@Param("equipId") String equipId, @Param("userCode") String userCode, @Param("now") Date now);
}
