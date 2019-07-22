package com.landleaf.ibsaas.web.web.service.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.domain.light.TLightType;
import com.landleaf.ibsaas.common.domain.light.vo.ProductReponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightProductVO;

import java.util.List;

public interface ITLightProductService<T> {

    TLightProduct addOrUpdateProduct(TLightProduct tLightProduct);

    Integer deleteProduct(Long id);

    /**
     * 根据条件分页查询产品列表
     * @param requestBody
     * @return
     */

    PageInfo<TLightProductVO> getProductRecordByCondition(QueryLightProductVO requestBody);

    /**
     * 获取灯光产品列表
     * @return
     */
    List<TLightProduct> getProducList();

    /**
     * 根据产品id获取产品信息
     * @param id
     * @return
     */
    ProductReponseVO getProcutById(Long id);


}
