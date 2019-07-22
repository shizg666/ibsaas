package com.landleaf.ibsaas.web.web.service.light;

import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.vo.LightPositionDeviceVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionRequestVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

public interface ITLightPositionService<T> extends IBaseService<T> {
    TLightPosition addOrUpdatePosition(TLightPositionRequestVO requestBody);

    Integer deletePositionById(Long id);

    Integer deletePositionByDeviceId(Long id);

    /**
     * 根据楼层获取灯光位置信息
     * @param id
     * @return
     */

    List<TLightPositionResponseVO> getPositionAtrributeListByFloor(Long id);

    /**
     * 获取尚未配饰位置的设备信息
     * @return
     */
    List<LightPositionDeviceVO> getUnPositionDeviceList();
}
