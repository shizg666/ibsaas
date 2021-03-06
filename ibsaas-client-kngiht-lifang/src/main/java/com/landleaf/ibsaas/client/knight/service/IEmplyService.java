package com.landleaf.ibsaas.client.knight.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.QueryEmplyDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 用户管理服务
 * @param <T>
 */
public interface IEmplyService<T> extends IBaseService<T> {


    PageInfo selectEmply(QueryEmplyDTO requestBody);

    int deleteEmply(Integer sysNo);
}
