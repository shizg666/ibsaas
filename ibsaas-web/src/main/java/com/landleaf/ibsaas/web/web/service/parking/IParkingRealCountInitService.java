package com.landleaf.ibsaas.web.web.service.parking;


import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 车位初始化相关配置
 * @param <T>
 */
public interface IParkingRealCountInitService<T> extends IBaseService<T> {


    /**
     * 获取停车系统初始配置（入参暂无，后期根据设备进行扩展）
     * @return
     */
    UsercrdtmRealCountQueryDTO queryRealCountInitParams(String clientId);

    /**
     * 修改
     * @param dto
     * @return
     */
    ParkingRealCountInit updateRealCountInitParams(UsercrdtmRealCountQueryDTO dto);

    /**
     * 新增
     * @param dto
     * @return
     */
    ParkingRealCountInit saveRealCountInitParams(UsercrdtmRealCountQueryDTO dto);
}
