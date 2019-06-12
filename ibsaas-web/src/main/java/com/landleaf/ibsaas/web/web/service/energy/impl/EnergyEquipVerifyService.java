package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.landleaf.ibsaas.common.dao.energy.EnergyEquipVerifyDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipVerify;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Lokiy
 * @date 2019/6/12 14:53
 * @description:
 */
@Service
@Slf4j
public class EnergyEquipVerifyService extends AbstractBaseService<EnergyEquipVerifyDao, EnergyEquipVerify> implements IEnergyEquipVerifyService {
}
