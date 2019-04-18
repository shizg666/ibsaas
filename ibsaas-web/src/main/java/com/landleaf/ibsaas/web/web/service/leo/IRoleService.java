package com.landleaf.ibsaas.web.web.service.leo;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.vo.RoleQueryInfoVO;

import java.util.List;

/**
 * @author wyl
 * @version 1.0
 * @description 角色信息维护
 * @date 2019/3/20 0020 17:17
 */
public interface IRoleService {

    /**
     * @param userRole
     * @return java.util.List<Role>
     * @description 列出用户对应的角色列表
     * @author wyl
     * @date 2019/3/20 0020 17:17
     * @version 1.0
     */
    List<Role> listUserRoles(UserRole userRole);

    /**
     * @param systemCode
     * @return java.util.List<com.landleaf.leo.common.domain.Role>
     * @description 获取指定系统下所有有效的角色
     * @author wyl
     * @date 2019/3/20 0020 17:17
     * @version 1.0
     */
    List<Role> listSystemRoles(String systemCode);

    /**
     * @param selectorParamsDto
     * @return com.github.pagehelper.PageInfo
     * @description 根据角色名称分页模糊查询名称列表
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    PageInfo queryRole(SelectorParamsDto selectorParamsDto);

    /**
     * @param roleQueryInfo
     * @return com.github.pagehelper.PageInfo
     * @description 根据条件分页获取角色列表
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    PageInfo listRoleQueryInfo(RoleQueryInfoVO roleQueryInfo);

    /**
     * @param role
     * @return com.landleaf.leo.common.domain.Role
     * @description 新增角色
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    Role addRole(Role role);

    /**
     * @param id
     * @return com.landleaf.leo.common.domain.Role
     * @description 根据角色主健id查询
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    Role queryRoleById(String id);

    /**
     * @param id
     * @param role
     * @return com.landleaf.leo.common.domain.Role
     * @description 修改角色信息
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    Role updateRole(String id, Role role);

    /**
     * @param roleIds
     * @return void
     * @description 根据角色主键批量删除
     * @author wyl
     * @date 2019/3/20 0020 17:18
     * @version 1.0
     */
    void deleteRoleByIds(String[] roleIds);

    /**
     * @param roleResource
     * @return java.util.List<Resource>
     * @description 获取角色下所有权限
     * @author wyl
     * @date 2019/3/20 0020 17:19
     * @version 1.0
     */
    List<Resource> listRoleAllResources(RoleResource roleResource);

    /**
     * @param roleCode
     * @param systemCode
     * @param resourceCodes
     * @return void
     * @description 关联角色权限信息
     * @author wyl
     * @date 2019/3/20 0020 17:19
     * @version 1.0
     */
    void updateRoleResource(String roleCode, String systemCode, String[] resourceCodes);

    /**
     * @param id
     * @return void
     * @description 根据角色主键删除
     * @author wyl
     * @date 2019/3/20 0020 17:19
     * @version 1.0
     */
    void deleteRoleById(String id);
}
