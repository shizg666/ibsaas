package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TBuildingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TBuilding record);

    int insertBatch(List<TBuilding> records);

    int insertSelective(TBuilding record);

    TBuilding selectByPrimaryKey(Long id);

    List<TBuilding> getAllBuildingByType(@Param("type") Integer type);

    TBuilding getAllBuildingByTypeAndName(@Param("type") Integer type,@Param("name") String name);

    List<TBuilding> getAllBuilding();

    int updateByPrimaryKeySelective(TBuilding record);

    int updateByPrimaryKey(TBuilding record);
}