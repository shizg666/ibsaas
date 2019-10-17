package com.landleaf.ibsaas.web.web.service.light;

import com.landleaf.ibsaas.common.domain.light.TLightType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITLightTypeService<T> {

    /**
     * 获取产品类型列表
     * @return
     */
    List<TLightType> getTypeList();

    List<TLightType> getTypeListByIds(List<Long> ids);

   TLightType selectByid(Long id);


}
