package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.UserDao;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.service.leo.ICommonUserService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonUserService extends AbstractBaseService<UserDao, User> implements ICommonUserService<User> {
    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名获取用户信息
     *
     * @param userCode
     * @return
     * @author wyl
     * @date 2017年08月07日15:53:00
     */
    public User getUser(String userCode) {
        return userDao.getUser(userCode);
    }
}
