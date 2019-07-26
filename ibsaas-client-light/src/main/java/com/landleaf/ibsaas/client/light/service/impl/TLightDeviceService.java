package com.landleaf.ibsaas.client.light.service.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.light.service.ITLightDeviceService;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.dao.light.TLightPositionDao;
import com.landleaf.ibsaas.common.dao.light.TLightProductDao;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceFloorVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TLightDeviceService extends AbstractBaseService<TLightDeviceDao, TLightDevice> implements ITLightDeviceService<TLightDevice> {

    @Autowired
    private TLightProductDao tLightProductDao;
    @Autowired
    private TLightPositionDao tLightPositionDao;

    @Override
    public List<TLightDevice> getDeviceList() {
        List<TLightDevice> tLightDevices = selectAll();
        if (tLightDevices == null){
            return Lists.newArrayListWithCapacity(0);
        }
        return tLightDevices;
    }

    @Override
    public LightDeviceResponseVO getDeviceById(Long id) {
        LightDeviceResponseVO result = new LightDeviceResponseVO();
        TLightDevice tLightDevice = selectByPrimaryKey(id);
        BeanUtils.copyProperties(tLightDevice,result);
        return result;
    }

    @Override
    public List<LightDeviceFloorVO> deviceAutoMonitor() {
        List<LightDeviceFloorVO> lightDeviceFloorVOS = tLightPositionDao.getDeviceFloor();
        if (lightDeviceFloorVOS == null){
            return Lists.newArrayListWithCapacity(0);
        }
        return lightDeviceFloorVOS;
    }


}
