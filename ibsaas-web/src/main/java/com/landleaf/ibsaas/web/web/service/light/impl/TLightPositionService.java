package com.landleaf.ibsaas.web.web.service.light.impl;

import com.landleaf.ibsaas.common.dao.light.TLightPositionDao;
import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import org.springframework.stereotype.Service;

@Service
public class TLightPositionService extends AbstractBaseService<TLightPositionDao, TLightPosition> implements ITLightPositionService<TLightPosition> {
}
