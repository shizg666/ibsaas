package com.landleaf.ibsaas.datasource.mybatis.service;


import com.landleaf.ibsaas.datasource.mybatis.service.delete.IDeleteService;
import com.landleaf.ibsaas.datasource.mybatis.service.insert.IInsertService;
import com.landleaf.ibsaas.datasource.mybatis.service.select.ISelectService;
import com.landleaf.ibsaas.datasource.mybatis.service.update.IUpdateService;

/**
 * @author
 * @version V1.0
 * @title: IDeleteService
 * @description 基础service公共接口
 * @date 2017/8/6
 */
public interface IBaseService<T> extends IDeleteService<T>, IInsertService<T>, IUpdateService<T>, ISelectService<T> {

}
