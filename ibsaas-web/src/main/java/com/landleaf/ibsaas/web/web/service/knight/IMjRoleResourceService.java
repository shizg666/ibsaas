package com.landleaf.ibsaas.web.web.service.knight;


import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 门禁角色权限操作
 * @param <T>
 */
public interface IMjRoleResourceService<T> extends IBaseService<T> {

    List<MjRoleResource> findRoleResourceByRoleId(String mjRoleId);
}
