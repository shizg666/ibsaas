package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.INewFanService;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.NewFan;
import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description: 四效新风机业务类
 */
@Service
@AllArgsConstructor
@Slf4j
public class NewFanService implements INewFanService {

    private final ICommonDeviceService iCommonDeviceService;


    @Override
    public void update(NewFanDTO newFanDTO) {
        iCommonDeviceService.writeDevice(newFanDTO);
    }


}
