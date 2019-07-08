package com.landleaf.ibsaas.common.dao.hvac.modbus;

import com.landleaf.ibsaas.common.domain.hvac.modbus.MbNode;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/5 16:04
 * @description:
 */
public interface MbNodeDao extends BaseDao<MbNode> {

    /**
     * 查询对应id
     * @param maxId
     * @return
     */
    List<MbNode> getMbNodeLmt(@Param("maxId") Integer maxId);

    /**
     * 获取所有节点
     * @return
     */
    List<MbNode> all();

    /**
     * 根据mb类型查询节点
     * @param mbType
     * @return
     */
    List<MbNode> getMbNodes(@Param("mbType") Integer mbType);
}
