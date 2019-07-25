package com.landleaf.ibsaas.web.web.service.light.impl;

import com.landleaf.ibsaas.common.dao.light.TFloorConfigDao;
import com.landleaf.ibsaas.common.domain.light.TFloorConfig;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ITFloorConfigService;
import org.springframework.stereotype.Service;

@Service
public class TFloorConfigService extends AbstractBaseService<TFloorConfigDao, TFloorConfig> implements ITFloorConfigService<TFloorConfig> {

}
