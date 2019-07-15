package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightAttribute;
import com.landleaf.ibsaas.common.domain.light.vo.LightProductAttributeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLightAttributeDao {


    List<TLightAttribute> getAttributeListByTypeIds(@Param("ids") List<Long> typeIds);

    List<LightProductAttributeVO> getAttributeListByProductIds(@Param("ids") List<Long> typeIds);
}