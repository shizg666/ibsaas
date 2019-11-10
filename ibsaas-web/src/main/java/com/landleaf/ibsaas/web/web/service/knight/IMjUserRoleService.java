package com.landleaf.ibsaas.web.web.service.knight;


import com.landleaf.ibsaas.common.domain.knight.emply.MjUserRole;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 门禁用户角色操作
 *
 * @param <T>
 */
public interface IMjUserRoleService<T> extends IBaseService<T> {

    /**
     * 用户绑定角色
     * @param mjUserId
     * @param mjRoleIds
     * @return
     */
    int userBindRole(Integer mjUserId, List<String> mjRoleIds);

    List<MjRole> getUserRoleBySysNo(Integer sysNo);

    List<MjUserRole> getUserRoleByRoleId(String roleId);
}
