package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TBuilding;

import java.util.List;

public interface TBuildingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TBuilding record);

    int insertSelective(TBuilding record);

    TBuilding selectByPrimaryKey(Long id);

    List<TBuilding> getAllBuilding();

    int updateByPrimaryKeySelective(TBuilding record);

    int updateByPrimaryKey(TBuilding record);
}