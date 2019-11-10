package com.landleaf.ibsaas.common.dao.knight;


import com.landleaf.ibsaas.common.domain.knight.TControlDoorAssociation;

public interface TControlDoorAssociationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TControlDoorAssociation record);

    int insertSelective(TControlDoorAssociation record);

    TControlDoorAssociation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TControlDoorAssociation record);

    int updateByPrimaryKey(TControlDoorAssociation record);
}