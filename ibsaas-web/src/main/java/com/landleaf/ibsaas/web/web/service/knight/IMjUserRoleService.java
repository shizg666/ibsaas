package com.landleaf.ibsaas.web.web.service.knight;


import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 门禁用户角色操作
 *
 * @param <T>
 */
public interface IMjUserRoleService<T> extends IBaseService<T> {

    int userBindRole(Integer mjUserId, String mjRoleId);
}
