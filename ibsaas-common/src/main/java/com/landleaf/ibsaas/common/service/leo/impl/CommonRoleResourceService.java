package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.RoleResourceDao;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.common.service.leo.ICommonRoleResourceService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonRoleResourceService extends AbstractBaseService<RoleResourceDao, RoleResource> implements ICommonRoleResourceService<RoleResource> {
    @Autowired
    private RoleResourceDao roleResourceDao;

    /**
     * @param roleResource
     * @return java.util.List<com.landleaf.leo.common.domain.Resource>
     * @description 列出角色下的所有菜单
     * @author wyl
     * @date 2019/3/20 0020 16:51
     * @version 1.0
     */
    public List<Resource> listRoleResources(RoleResource roleResource) {
        RoleResource queryParam = new RoleResource();
        queryParam.setBelongSystem(roleResource.getBelongSystem());
        queryParam.setRoleCode(roleResource.getRoleCode());
        return roleResourceDao.listRoleResources(queryParam);
    }
}
