package com.landleaf.ibsaas.common.service.leo.impl;

import com.landleaf.ibsaas.common.dao.leo.SubSystemDao;
import com.landleaf.ibsaas.common.domain.leo.SubSystem;
import com.landleaf.ibsaas.common.service.leo.ICommonSubSystemService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.stereotype.Service;

@Service
public class CommonSubSystemService extends AbstractBaseService<SubSystemDao, SubSystem> implements ICommonSubSystemService<SubSystem> {
}
