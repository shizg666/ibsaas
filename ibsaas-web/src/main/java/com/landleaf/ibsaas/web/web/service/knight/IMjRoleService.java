package com.landleaf.ibsaas.web.web.service.knight;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;
import com.landleaf.ibsaas.web.web.dto.knight.role.WebMjRoleDTO;

/**
 * 门禁角色操作
 *
 * @param <T>
 */
public interface IMjRoleService<T> extends IBaseService<T> {

    PageInfo<MjRole> getPageInfo(String name, Integer departId, int page, int limit);
}
