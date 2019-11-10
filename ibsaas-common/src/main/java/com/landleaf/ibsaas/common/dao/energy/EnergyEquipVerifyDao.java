package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquipVerify;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyEquipVerifyDao extends BaseDao<EnergyEquipVerify> {

    /**
     * 把校验状态更新为未启用
     * @param equipId
     * @param userCode
     * @param now
     * @return
     */
    int updateUnEnableByEquipId(@Param("equipId") String equipId, @Param("userCode") String userCode, @Param("now") Date now);
}
