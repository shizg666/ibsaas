package com.landleaf.ibsaas.client.knight.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.client.knight.domain.dto.control.QueryMjDoorOpenRecordDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 打卡原始记录
 * @param <T>
 */
public interface IKQCardRecordService<T> extends IBaseService<T> {


    PageInfo mjOpenDoorRecordByDb(QueryMjDoorOpenRecordDTO requestBody);
}
