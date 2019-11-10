package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IFanCoilService;
import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
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
public class FanCoilService implements IFanCoilService {

    private final ICommonDeviceService iCommonDeviceService;

    @Override
    public void update(FanCoilDTO fanCoilDTO) {
        iCommonDeviceService.writeDevice(fanCoilDTO);
    }
}
