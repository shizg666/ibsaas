package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightDeviceState;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TLightDeviceStateDao extends BaseDao<TLightDeviceState> {
    TLightDeviceState getByAdress(String address);
}
