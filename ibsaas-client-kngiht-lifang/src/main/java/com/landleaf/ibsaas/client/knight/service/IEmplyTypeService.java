package com.landleaf.ibsaas.client.knight.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.EmplyTypeDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 用户类型管理服务
 *
 * @param <T>
 */
public interface IEmplyTypeService<T> extends IBaseService<T> {


    List<EmplyTypeDTO> queryAllEmplyType();
}
