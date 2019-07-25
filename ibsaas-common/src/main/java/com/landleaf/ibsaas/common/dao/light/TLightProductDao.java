package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLightProductDao extends BaseDao<TLightProduct> {

    List<TLightProduct> getProductListByProductIds(@Param("id") List<Long> productIds);

    TLightProduct selectByid(Long id);

    int deleteByPrimaryId(Long id);


}