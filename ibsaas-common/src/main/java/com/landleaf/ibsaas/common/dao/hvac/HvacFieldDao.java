package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:
 */
public interface HvacFieldDao extends BaseDao<HvacField> {

    /**
     * 跑id
     * @param maxId
     * @return
     */
    List<HvacField> getHvacFieldLmt(@Param("maxId") Integer maxId);
}
