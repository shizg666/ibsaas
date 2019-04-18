package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.UserRoleDao;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.common.service.leo.ICommonUserRoleService;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommonUserRoleService extends AbstractBaseService<UserRoleDao, UserRole> implements ICommonUserRoleService<UserRole> {
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 获取用户角色列表
     *
     * @param userRole
     * @return
     * @author wyl
     * @date 2017年08月13日15:50:11
     */
    public List<Role> listUserRoles(UserRole userRole) {
        UserRole queryParam = new UserRole();
        queryParam.setUserCode(userRole.getUserCode());
        queryParam.setBelongSystem(userRole.getBelongSystem());
        return userRoleDao.listUserRoles(queryParam);
    }

    /**
     * 获取子系统所有角色
     *
     * @param systemCode
     * @return
     * @author wyl
     * @date 2017年08月13日17:21:51
     */
    public List<Role> listSystemRoles(String systemCode) {
        return null;
    }
}
