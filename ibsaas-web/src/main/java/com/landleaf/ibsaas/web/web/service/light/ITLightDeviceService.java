package com.landleaf.ibsaas.web.web.service.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceRequestVO;

public interface ITLightDeviceService<T> {
    TLightDevice addOrUpdateDevice(TLightDeviceRequestVO tLightDeviceRequestVO);

    Integer deleteDevice(Long id);

    PageInfo<LightDeviceResponseVO> getDeviceRecordByCondition(TLightDeviceQueryVO requestBody);

    LightDeviceResponseVO getDeviceById(Long id);
}
