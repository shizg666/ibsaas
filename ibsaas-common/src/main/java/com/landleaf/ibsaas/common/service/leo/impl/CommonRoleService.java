package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.RoleDao;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.service.leo.ICommonRoleService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.stereotype.Service;

@Service
public class CommonRoleService extends AbstractBaseService<RoleDao, Role> implements ICommonRoleService<Role> {
}
