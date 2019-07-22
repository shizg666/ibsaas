package com.landleaf.ibsaas.client.light.service;

import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;

import java.util.List;

public interface ITLightDeviceService<T> {

   List<TLightDevice> getDeviceList();

    LightDeviceResponseVO getDeviceById(Long id);

}
