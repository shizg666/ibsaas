package com.landleaf.ibsaas.client.parking.lifang.mq.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 进出记录相关操作
 *
 * @param <Usercrdtm>
 */
public interface IUsercrdtmService<Usercrdtm> extends IBaseService<Usercrdtm> {

    PageInfo pageQueryList(UsercrdtmListQueryDTO queryDTO);

    UsercrdtmRealCountQueryDTO realCount(UsercrdtmRealCountQueryDTO queryDTO);

    List<UsercrdtmRealCountQueryByHourDTO> realCountFHour(UsercrdtmRealCountQueryByHourDTO queryDTO);

    List<UsercrdtmInHistoryQueryDTO> trafficFlow(UsercrdtmInHistoryQueryDTO queryDTO);

    /**
     * 根据时间段查询数量
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    int selectCountBetween(String startTime, String endTime, Integer type);
}
