package com.landleaf.ibsaas.common.dao.hvac.modbus;

import com.landleaf.ibsaas.common.domain.hvac.modbus.MbField;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/5 16:04
 * @description:
 */
public interface MbFieldDao extends BaseDao<MbField> {

    /**
     * 查询对应id
     * @param maxId
     * @return
     */
    List<MbField> getMbFieldLmt(@Param("maxId") Integer maxId);
}
