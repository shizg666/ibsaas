package com.landleaf.ibsaas.web.web.service.light.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.light.TLightProductDao;
import com.landleaf.ibsaas.common.dao.light.TLightProductDeviceDao;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.service.light.ITLightProductService;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class TLightProductService extends AbstractBaseService<TLightProductDao, TLightProduct> implements ITLightProductService<TLightProduct> {
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private TLightProductDeviceDao tLightProductDeviceDao;


    @Override
    public TLightProduct addOrUpdateProduct(TLightProduct tLightProduct) {
        TLightProduct tLightProduct1;
        if (tLightProduct.getId() == null || tLightProduct.getId() == 0L){
            tLightProduct1 = addProduct(tLightProduct);
        }else {
            tLightProduct1 = updateProduct(tLightProduct);
        }
        return tLightProduct1;
    }

    @Override
    @Transactional
    public Integer deleteProduct(Long id) {
        Integer result = deleteByPrimaryKey(id);
        if (result < 0 ) {
            throw new BusinessException("产品删除失败");
        }
        tLightProductDeviceDao.deleteByProductId(id);
        return result;
    }

    @Override
    public PageInfo<TLightProduct> getProductRecordByCondition(QueryLightProductVO requestBody) {
        PageHelper.startPage(requestBody.getPage(), requestBody.getLimit(), true);
        Example example = new Example(TLightProduct.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(requestBody.getType())) {
            criteria.andEqualTo("type", requestBody.getType());
        }
        if (StringUtils.isNotBlank(requestBody.getProtocol())) {
            criteria.andEqualTo("protocol", requestBody.getProtocol());
        }
        if (StringUtils.isNotBlank(requestBody.getBrand())) {
            criteria.andEqualTo("brand", requestBody.getBrand());
        }
        if (StringUtils.isNotBlank(requestBody.getModel())) {
            criteria.andEqualTo("model", requestBody.getModel());
        }
        if (StringUtils.isNotBlank(requestBody.getName())) {
            criteria.andLike("name", "%" + requestBody.getName() + "%"); //模糊查询
        }
        List<TLightProduct> lightProducts = selectByExample(example);
        if (CollectionUtils.isEmpty(lightProducts)) {
            lightProducts = Lists.newArrayList();
        }
        return new PageInfo(lightProducts);
    }

    public TLightProduct addProduct(TLightProduct tLightProduct) {
        Date date = new Date();
        tLightProduct.setCtime(date);
        tLightProduct.setUtime(date);
        Integer result = saveSelective(tLightProduct);
        if (result < 0 ){
            throw new BusinessException("灯光产品添加失败");
        }
        return tLightProduct;
    }
    public TLightProduct updateProduct(TLightProduct tLightProduct) {
        Integer result = updateByPrimaryKeySelective(tLightProduct);
        if (result < 0 ){
            throw new BusinessException("灯光产品修改失败");
        }
        return tLightProduct;
    }
}
