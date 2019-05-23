package com.landleaf.ibsaas.common.dao.knight;

import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MjRoleResourceDao extends BaseDao<MjRoleResource> {
    int deleteByRoleId(String roleId);
    int insertBatch(List<MjRoleResource> mjRoleResources);
}
