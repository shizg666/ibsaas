package com.landleaf.ibsaas.web.web.service.parking;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.request.*;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * 停车业务相关操作
 */
public interface IParkingServeice {

    /**
     * 根据类型获取通道列表
     * @param queryDTO
     * @return
     */
    Response getChannelList(ChannelListQueryDTO queryDTO);

    /**
     * 获取所有收费列表
     * @return
     */
    Response queryAllChargerule();

    /**
     * 实时监控
     * @param queryDTO
     * @return
     */
    Response realCount(UsercrdtmRealCountQueryDTO queryDTO);

    /**
     * 获取分时段监控数据
     * @param queryDTO
     * @return
     */
    Map<String, Object>  realCountFHour(UsercrdtmRealCountQueryByHourDTO queryDTO);

    /**
     * 获取进出记录
     * @param queryDTO
     * @return
     */
    Object usercrdtmList(UsercrdtmListQueryDTO queryDTO);

    /**
     * 车辆列表
     * @param queryDTO
     * @return
     */
    Object userinfoList(UserinfoListQueryDTO queryDTO);

    /**
     * 车辆详情
     * @param queryDTO
     * @return
     */
    UserinfoResponseDTO userinfoDetail(UserinfoDetailQueryDTO queryDTO);

    List<UsercrdtmInHistoryQueryDTO> trafficFlow(UsercrdtmInHistoryQueryDTO queryDTO);
}
