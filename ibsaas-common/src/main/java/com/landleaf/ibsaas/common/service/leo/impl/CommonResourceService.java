package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.ResourceDao;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.common.service.leo.ICommonResourceService;
import com.landleaf.ibsaas.common.service.leo.ICommonRoleResourceService;
import com.landleaf.ibsaas.common.service.leo.ICommonUserRoleService;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommonResourceService extends AbstractBaseService<ResourceDao, Resource> implements ICommonResourceService<Resource> {
    @Autowired
    private ICommonUserRoleService commonUserRoleService;
    @Autowired
    private ICommonRoleResourceService commonRoleResourceService;

    /**
     * @param systemCode
     * @param userCode
     * @return java.util.Set<com.landleaf.leo.common.domain.Resource>
     * @description 加载用户在指定系统中所有可用菜单
     * @author wyl
     * @date 2019/3/20 0020 16:53
     * @version 1.0
     */
    public Set<Resource> listUserAllResources(String systemCode, String userCode) {
        Set<Resource> allResource = new HashSet<Resource>(); //所有菜单的去重集合
        //查询出用户所有角色
        UserRole queryRolesParams = new UserRole();
        queryRolesParams.setBelongSystem(systemCode);
        queryRolesParams.setUserCode(userCode);
        List<Role> roles = commonUserRoleService.listUserRoles(queryRolesParams);

        //查询出这些角色对应的所有菜单
        //角色菜单的查询条件
        RoleResource roleResourceQueryParam = new RoleResource();
        roleResourceQueryParam.setBelongSystem(systemCode);
        for (Role role : roles) {
            roleResourceQueryParam.setRoleCode(role.getRoleCode());
            List<Resource> resources = commonRoleResourceService.listRoleResources(roleResourceQueryParam);
            if (resources != null) {
                allResource.addAll(resources);
            }
        }
        return allResource;
    }


    /**
     * @param systemCode
     * @param entryUri
     * @return com.landleaf.leo.common.domain.Resource
     * @description 根据所属系统编码和entryUri获取唯一权限
     * @author wyl
     * @date 2019/3/20 0020 16:53
     * @version 1.0
     */
    @Override
    public Resource findResourceBySystemAndUri(String systemCode, String entryUri) {
        Resource queryParam = new Resource();
        queryParam.setActive(1);
        queryParam.setEntryUri(entryUri);
        queryParam.setBelongSystem(systemCode);
        return selectOne(queryParam);
    }
}
