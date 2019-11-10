package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IHydraulicModuleService;
import com.landleaf.ibsaas.common.domain.hvac.dto.HydraulicModuleDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
@Service
@Slf4j
@AllArgsConstructor
public class HydraulicModuleService implements IHydraulicModuleService {

    private ICommonDeviceService iCommonDeviceService;

    @Override
    public void update(HydraulicModuleDTO hydraulicModuleDTO) {
        iCommonDeviceService.writeDevice(hydraulicModuleDTO);
    }
}
