package com.landleaf.ibsaas.common.dao.light;


import com.landleaf.ibsaas.common.domain.light.TLightAreaDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceFloorVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLightAreaDeviceDao extends BaseDao<TLightAreaDevice> {

    List<TLightAreaDevice> getLightList(Long id);

    List<LightDeviceFloorVO> getAreaDeviceList();
}