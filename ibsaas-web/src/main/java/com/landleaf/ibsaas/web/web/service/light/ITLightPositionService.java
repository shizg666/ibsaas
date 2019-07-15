package com.landleaf.ibsaas.web.web.service.light;

import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

public interface ITLightPositionService<T> extends IBaseService<T> {
    TLightPosition addOrUpdatePosition(TLightPositionVO requestBody);

    Integer deletePosition(Long id);

    List<TLightPositionVO> getPositionAtrributeListByFloor(Long id);
}
