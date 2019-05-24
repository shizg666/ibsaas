package com.landleaf.ibsaas.common.dao.knight;

import com.landleaf.ibsaas.common.domain.knight.emply.MjUserRole;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MjUserRoleDao extends BaseDao<MjUserRole> {

    void saveUserRoles(@Param("list")List<MjUserRole> addUserRoles);

    int deleteUserRoles(@Param("mjUserId")Integer mjUserId, @Param("ids")Set<String> deleteUserRoleIds);
}
