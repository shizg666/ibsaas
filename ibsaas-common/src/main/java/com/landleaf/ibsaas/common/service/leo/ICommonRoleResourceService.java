package com.landleaf.ibsaas.common.service.leo;


import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 *角色菜单服务
 */
public interface ICommonRoleResourceService<T> extends IBaseService<T> {
    /**
     * @description 列出角色下的所有菜单
     * @param roleResource
     * @author wyl
     * @date 2019/3/20 0020 16:42
     * @return java.util.List<com.landleaf.leo.common.domain.Resource>
     * @version 1.0
    */
    List<Resource> listRoleResources(RoleResource roleResource);
}
