package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.NewFan;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;
import com.landleaf.ibsaas.web.web.service.hvac.ICommonDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.INewFanService;
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

    private final HvacNodeDao hvacNodeDao;

    @Override
    public List<NewFanVO> overview() {

        return (List<NewFanVO>) iCommonDeviceService.getCurrentData(3002);
    }

    @Override
    public NewFanVO getInfoById(String id) {
        NewFanVO newFanVO = new NewFanVO();
        HvacNodeVO hvacNodeVO = hvacNodeDao.getHvacNodeByNodeId(id);
        NewFan newFan =  iCommonDeviceService.getCurrentInfo(hvacNodeVO);
        BeanUtils.copyProperties(newFan, newFanVO);
        return newFanVO;
    }


}
