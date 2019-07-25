package com.landleaf.ibsaas.web.web.service.light.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.light.TLightTypeDao;
import com.landleaf.ibsaas.common.domain.light.TLightType;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ITLightTypeService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TLightTypeService extends AbstractBaseService<TLightTypeDao, TLightType> implements ITLightTypeService<TLightType> {

    @Override
    public List<TLightType> getTypeList() {
        List<TLightType> tLightDevice= selectAll();
        return tLightDevice;
    }

    @Override
    public List<TLightType> getTypeListByIds(List<Long> ids) {
        Example example = new Example(TLightType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        List<TLightType> result = selectByExample(example);
        if (result == null){
            return Lists.newArrayListWithCapacity(0);
        }
        return result;
    }
}
