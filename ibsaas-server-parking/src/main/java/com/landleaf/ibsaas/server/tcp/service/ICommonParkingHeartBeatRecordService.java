package com.landleaf.ibsaas.server.tcp.service;


import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 心跳保持记录操作
 * @param <T>
 */
public interface ICommonParkingHeartBeatRecordService<T> extends IBaseService<T> {


    /**
     * 查找最近一次心跳记录
     * @return
     */
    void updateLatestedRecord(HeartBeatDTO requestBody);
}
