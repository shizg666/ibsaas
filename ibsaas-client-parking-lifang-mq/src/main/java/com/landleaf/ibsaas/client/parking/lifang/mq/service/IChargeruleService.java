package com.landleaf.ibsaas.client.parking.lifang.mq.service;


import com.landleaf.ibsaas.common.domain.parking.response.ChargeruleResponseDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 收费类型相关操作
 *
 * @param <Chargerule>
 */
public interface IChargeruleService<Chargerule> extends IBaseService<Chargerule> {

    List<ChargeruleResponseDTO> queryAllChargerule();
}
