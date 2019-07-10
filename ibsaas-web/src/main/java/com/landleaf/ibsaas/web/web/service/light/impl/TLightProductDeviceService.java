package com.landleaf.ibsaas.web.web.service.light.impl;

import com.landleaf.ibsaas.common.dao.light.TLightProductDeviceDao;
import com.landleaf.ibsaas.common.domain.light.TLightProductDevice;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ILightProductDeviceService;
import org.springframework.stereotype.Service;

@Service
public class TLightProductDeviceService extends AbstractBaseService<TLightProductDeviceDao, TLightProductDevice> implements ILightProductDeviceService<TLightProductDevice> {
}
