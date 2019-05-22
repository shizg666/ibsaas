package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TDoor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDoorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDoor record);

    int insertSelective(TDoor record);

    TDoor selectByPrimaryKey(Long id);

    TDoor selectByName(String name);

    List<TDoor> selectParentIds(@Param("ids") List<Long> ids);

    List<TDoor> selectParentId(Long id);

    int updateByPrimaryKeySelective(TDoor record);

    int updateByPrimaryKey(TDoor record);
}