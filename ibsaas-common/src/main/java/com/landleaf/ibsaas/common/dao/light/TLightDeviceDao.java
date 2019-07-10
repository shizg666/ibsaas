package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;

import java.util.List;


public interface TLightDeviceDao extends BaseDao<TLightDevice> {

    List<LightDeviceResponseVO> getDeviceRecordByCondition(TLightDeviceQueryVO requestBody);
}