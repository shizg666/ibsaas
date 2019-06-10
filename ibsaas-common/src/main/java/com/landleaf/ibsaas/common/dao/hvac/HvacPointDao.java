package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:
 */
public interface HvacPointDao extends BaseDao<HvacPoint> {
    /**
     * 根据节点和字段 获取点位
     * @param deviceId
     * @param nodeId
     * @param fieldId
     * @return
     */
    List<HvacPoint> getHvacPointDaoByNodeIdOrFieldId(@Param("deviceId") String deviceId, @Param("nodeId") String nodeId, @Param("fieldId") String fieldId);


    /**
     * 把小于多少id的数据取出 用作跑批
     * @param maxId
     * @return
     */
    List<HvacPoint> getHvacPointLmt(@Param("maxId") Integer maxId);
}
