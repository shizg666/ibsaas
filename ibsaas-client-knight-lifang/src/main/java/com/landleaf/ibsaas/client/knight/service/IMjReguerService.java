package com.landleaf.ibsaas.client.knight.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.knight.control.QueryRegisterUserByDbDTO;
import com.landleaf.ibsaas.common.domain.knight.control.RegisterUserByDbDTO;
import com.landleaf.ibsaas.common.domain.knight.control.UnRegisterUserByDbDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 门禁注册人员
 * @param <T>
 */
public interface IMjReguerService<T> extends IBaseService<T> {


    int registeruser(RegisterUserByDbDTO requestBody);
    //解除权限
    int unregisteruser(UnRegisterUserByDbDTO requestBody);

    PageInfo queryRegisteruserByDb(QueryRegisterUserByDbDTO requestBody);
}
