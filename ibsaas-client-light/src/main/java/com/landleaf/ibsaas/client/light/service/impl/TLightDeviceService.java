package com.landleaf.ibsaas.client.light.service.impl;

import com.landleaf.ibsaas.client.light.service.ITLightDeviceService;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TLightDeviceService extends AbstractBaseService<TLightDeviceDao, TLightDevice> implements ITLightDeviceService<TLightDevice> {


    @Override
    public LightDeviceResponseVO getDeviceById(Long id) {
        LightDeviceResponseVO result = new LightDeviceResponseVO();
        TLightDevice tLightDevice = selectByPrimaryKey(id);
        BeanUtils.copyProperties(tLightDevice,result);
        return result;
    }


}
