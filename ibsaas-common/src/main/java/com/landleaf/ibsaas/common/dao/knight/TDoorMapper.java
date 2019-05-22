package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDoorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDoor record);

    int insertSelective(TDoor record);

    TDoor selectByPrimaryKey(Long id);

    TDoor selectByName(String name);

    List<TDoor> selectByParentIds(@Param("ids") List<Long> ids);

    List<TDoor> selectByParentId(Long id);

    List<TDoor> getDoorListOrderByfloor();

    TDoor selectByContrloId(Long controlId);

    List<TDoor> getDoorInfoByControlIds(@Param("ids") List<Long> controlIds);

    int updateByPrimaryKeySelective(TDoor record);

    int updateByPrimaryKey(TDoor record);

    int bindingDoorControl(@Param("id") Long id,@Param("controId") Long controId);



}