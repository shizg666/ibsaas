package com.landleaf.ibsaas.common.service.leo;


import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * @description 用户角色信息
 * @author wyl
 * @date 2019/3/20 0020 16:45
 * @version 1.0
*/
public interface ICommonUserRoleService<T> extends IBaseService<T> {

    /**
     * @description 获取用户角色列表
     * @param userRole
     * @author wyl
     * @date 2019/3/20 0020 16:45
     * @return java.util.List<com.landleaf.leo.common.domain.Role>
     * @version 1.0
    */
    List<Role> listUserRoles(UserRole userRole);

    /**
     * @description 获取子系统所有角色
     * @param systemCode
     * @author wyl
     * @date 2019/3/20 0020 16:45
     * @return java.util.List<com.landleaf.leo.common.domain.Role>
     * @version 1.0
    */
    List<Role> listSystemRoles(String systemCode);
}
