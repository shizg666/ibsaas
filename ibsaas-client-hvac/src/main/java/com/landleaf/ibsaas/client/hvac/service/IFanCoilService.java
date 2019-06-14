package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
public interface IFanCoilService {

    /**
     * 更新某项值
     * @param fanCoilDTO
     */
    void update(FanCoilDTO fanCoilDTO);
}
