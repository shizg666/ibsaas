package com.landleaf.ibsaas.web.web.service.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;

public interface ITLightProductService<T> {

    TLightProduct addOrUpdateProduct(TLightProduct tLightProduct);

    Integer deleteProduct(Long id);

    PageInfo<TLightProduct> getProductRecordByCondition(QueryLightProductVO requestBody);
}
