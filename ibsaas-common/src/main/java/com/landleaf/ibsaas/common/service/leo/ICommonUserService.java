package com.landleaf.ibsaas.common.service.leo;


import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * @description 用户信息相关操作
 * @author wyl
 * @date 2019/3/20 0020 16:44
 * @version 1.0
*/
public interface ICommonUserService<T> extends IBaseService<T> {

    /**
     * @description 根据用户名获取用户信息
     * @param userCode
     * @author wyl
     * @date 2019/3/20 0020 16:44
     * @return com.landleaf.leo.common.domain.User
     * @version 1.0
    */
    User getUser(String userCode);

}
