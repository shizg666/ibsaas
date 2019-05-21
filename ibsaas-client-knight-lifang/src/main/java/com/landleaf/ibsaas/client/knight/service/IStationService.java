package com.landleaf.ibsaas.client.knight.service;


import com.landleaf.ibsaas.common.domain.knight.control.Station;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 门禁设备服务
 *
 * @param <T>
 */
public interface IStationService<T> extends IBaseService<T> {


    List<Station> getMjDeviceByIdsDb(List<Integer> deviceSysIds);
}
