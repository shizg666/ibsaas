package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TFloor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFloorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TFloor record);

    void insertBatch(List<TFloor> records);

    int insertSelective(TFloor record);

    TFloor selectByPrimaryKey(Long id);

    List<TFloor> selectAll();

    TFloor selectByFloor(@Param("floorId") Integer floorId,@Param("parentId") Long parentId);

    List<TFloor> selectByParentIds(@Param("ids") List<Long> ids);

    List<TFloor> selectByPrimaryKeys(@Param("ids") List<Long> ids);

    List<TFloor> selectByParentId(Long id);

    int updateByPrimaryKeySelective(TFloor record);

    int updateByPrimaryKey(TFloor record);


}