package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.dto.HydraulicModuleDTO;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
public interface IHydraulicModuleService {

    /**
     * 更新某项值
     * @param hydraulicModuleDTO
     */
    void update(HydraulicModuleDTO hydraulicModuleDTO);
}
