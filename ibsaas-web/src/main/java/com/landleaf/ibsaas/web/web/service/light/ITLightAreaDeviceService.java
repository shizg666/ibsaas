package com.landleaf.ibsaas.web.web.service.light;

import com.landleaf.ibsaas.common.domain.light.vo.TLightAreaResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

public interface ITLightAreaDeviceService<T> extends IBaseService<T> {
    List<TLightAreaResponseVO> getPositionAtrributeListByFloor(Long id);
}
