package com.landleaf.ibsaas.client.light.service;

import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceFloorVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;

import java.util.List;

public interface ITLightDeviceService<T> {

   List<TLightDevice> getDeviceList();

    LightDeviceResponseVO getDeviceById(Long id);

    /**
     * 项目启动时自动订阅已配置的设备
     * @return
     */
    List<LightDeviceFloorVO> deviceAutoMonitor();

}
