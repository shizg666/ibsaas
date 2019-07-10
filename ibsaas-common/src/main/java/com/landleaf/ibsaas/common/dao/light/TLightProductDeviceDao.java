package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightProductDevice;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TLightProductDeviceDao extends BaseDao<TLightProductDevice> {


    int deleteByProductId(Long id);
    int deleteByDeviceId(Long id);
}