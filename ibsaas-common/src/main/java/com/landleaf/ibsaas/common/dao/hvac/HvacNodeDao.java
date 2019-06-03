package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeFieldVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:
 */
public interface HvacNodeDao extends BaseDao<HvacNode> {


    /**
     * 获取节点  包含所含字段数据
     * @param deviceId
     * @return
     */
    List<HvacNodeVO> getHvacNodeByDeviceId(String deviceId);

    /**
     * 根据节点id获取某个节点的数据
     * @param id
     * @return
     */
    HvacNodeVO getHvacNodeByNodeId(String id);


    /**
     * 根据节点id和字段名 获取点位信息
     * @param nodeId
     * @param fieldName
     * @return
     */
    HvacNodeFieldVO getHvacNodeFieldVO(@Param("nodeId") String nodeId, @Param("fieldName") String fieldName);

}
