package com.landleaf.ibsaas.datasource.mybatis.service.insert;

/**
 * @author
 * @version V1.0
 * @title: IDeleteService
 * @description 保存 service
 * @date 2017/8/6
 */
public interface IInsertService<T> {

    /**
     * 保存记录
     *
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 保存记录
     * 属性为null的不会保存使用数据库默认值
     *
     * @param record
     * @return
     */
    int saveSelective(T record);
}
