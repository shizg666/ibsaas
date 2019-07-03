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
     * 获取所有的节点
     * @return
     */
    List<HvacNode> all();

    /**
     * 获取节点  包含所含字段数据
     * @param deviceType
     * @return
     */
    List<HvacNodeVO> getHvacNodeByDeviceId(@Param("deviceType") Integer deviceType);

    /**
     * 根据设备点位获取设备节点数据
     * @param deviceType
     * @return
     */
    List<HvacNodeVO> getHvacNodeByDeviceType(@Param("deviceType") Integer deviceType);
    /**
     * 根据设备点位获取设备节点数据没绑定设备的
     * @param deviceType
     * @return
     */
    List<HvacNodeVO> getHvacNodeByDeviceTypeWithoutEquip(@Param("deviceType") Integer deviceType);

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

    /**
     *  根据带入的字段属性 查询点位信息
     * @param nodeId
     * @param fieldNames
     * @return
     */
    List<HvacNodeFieldVO> getHvacNodeFieldVOList(@Param("nodeId") String nodeId, @Param("fieldNames") List<String> fieldNames);

    /**
     * 根据某项属性查找批量所以节点的该属性 用于批量更改的时候
     * @param fieldName
     * @return
     */
    List<HvacNodeFieldVO> getHvacNodeFieldVOByFieldName(@Param("fieldName") String fieldName);

    /**
     * 根据设备点位获取 所有节点信息
     * @param deviceType
     * @return
     */
    List<HvacNode> getHvacNodes(@Param("deviceType") Integer deviceType);
}
